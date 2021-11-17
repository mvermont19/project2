// readcsvdf.scala or readcsv.scala
package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

object FromCSVFile {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[3]")
      .appName("Synergy")
      .getOrCreate()
    val sc = spark.sparkContext

// Read CSV file into DataFrame.

// Treat the first row as header. Use .load() or .csv()

// // Read multiple CSV files.
// val df = spark.read.csv("path1,path2,path3")

// // Read all CSV files in a directory.
// val df3 = spark.read.csv("Folder path")


// Automatically infers column types based on the data.
// Default value set to this option is false
    val df5 = spark.read
      .options(Map("header" -> "true", "inferSchema" -> "true", "delimiter" -> ","))
      .csv("hdfs:///user/maria_dev/HDFSalphaVantageFiles/")
    df5.printSchema()

// Reading CSV files with a user-specified custom schema.
    val schema = new StructType()
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
  }
}