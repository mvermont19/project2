// readcsvdf.scala or readcsv.scala
package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
//import javax.lang.model.`type`.ArrayType
import scala.runtime.LongRef
import org.apache.spark.sql.{Row, SparkSession}


object FromCSVFile {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[3]")
      .appName("Synergy")
      .getOrCreate()
    val sc = spark.sparkContext
// Read CSV file into DataFrame.
    import spark.implicits._
// Treat the first row as header. Use .load() or .csv()

// // Read multiple CSV files.
// val df = spark.read.csv("path1,path2,path3")

// // Read all CSV files in a directory.
// val df3 = spark.read.csv("Folder path")


// Automatically infers column types based on the data.
// Default value set to this option is false
    val multiline_df = spark.read.option("multiline", "true").json("hdfs:///user/maria_dev/Twitter/twitter.json")
    println("read multiline json file...")
    multiline_df.show(false)
    multiline_df.printSchema()
  
    val simpleSchema = new StructType()
            .add("data", ArrayType(new StructType()
            .add("author_id", StringType)
            .add("id", StringType)
            .add("text", StringType)))
            
            
            /*StructField("author_id", StringType, true),
            StructField("id", StringType, true),
            StructField("text", StringType, true)
      )))*/
      .add("includes", new StructType()
            .add("users", ArrayType(new StructType()
            .add("id", StringType)
            .add("name", StringType)
            .add("username", StringType)))
      )
            
      .add("meta", new StructType()
        .add("newest_id", StringType)
        .add("oldest_id", StringType)
        .add("result_count", LongType)
      )
    
     val df_with_schema = spark.read.schema(simpleSchema).json("hdfs:///user/maria_dev/Twitter/twitter.json")
    df_with_schema.printSchema()
    df_with_schema.show(true)

    df_with_schema.createOrReplaceTempView("tweets")
    //val tweetQuery = spark.sql("SELECT data FROM tweets")
    val resultCount = df_with_schema.select($"meta".getField("result_count")).show(false)
    println(resultCount)
    df_with_schema.select($"data".getItem(0)("text"), $"includes".getItem("users")(0)("name")).show(false)
    //val resultQuery = spark.sql("SELECT meta FROM tweets")
    //val resultCount = resultQuery.map(result=> result(2)).show()
    //tweetQuery.map(tweets => "Text: " + tweets(0)).show(false)
  }
}
// Reading CSV files with a user-specified custom schema.
    /*val schema = new StructType()
      .add("timestamp", StringType, true)
      .add("high", DoubleType, true)
      .add("low", DoubleType, true)
      .add("close", DoubleType, true)
      .add("open", DoubleType, true)
      .add("openhigh", DoubleType, true)
      .add("openlow", DoubleType, true)
      .add("closetwo", DoubleType, true)
      .add("volume", DoubleType, true)
      .add("market cap", DoubleType, true)

    val df_with_schema = spark.read
      .format("csv")
      .option("header", true)
      .schema(schema)
      .load("hdfs:///user/maria_dev/HDFSalphaVantageFiles/")
    df_with_schema.printSchema()
    df_with_schema.show(false)
    df_with_schema.createOrReplaceTempView("data")
    val sqlDef = spark.sql("SELECT * FROM data WHERE timestamp > 2021-11-10")
    sqlDef.show()*/
// Write Spark DataFrame to CSV file.
   /* df_with_schema.write
      .option("header", "true")
      .csv("file:///tmp/spark_output/zipcodes")
*/
// Write mode samples:
// df2.write.mode(SaveMode.Append).csv("/tmp/spark_output/zipcodes")
// df2.write.mode(SaveMode.Overwrite).csv("/tmp/spark_output/zipcodes")
// df2.write.mode(SaveMode.Ignore).csv("/tmp/spark_output/zipcodes")
// df2.write.mode(SaveMode.ErrorIfExists).csv("/tmp/spark_output/zipcodes")

    //df4.write.mode("append").csv("file:///tmp/spark_output/zipcodes")
  //}
//}