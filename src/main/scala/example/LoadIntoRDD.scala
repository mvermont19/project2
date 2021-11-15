// readcsvdf.scala or readcsv.scala
package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
//import javax.lang.model.`type`.ArrayType
import scala.runtime.LongRef
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path

object FromCSVFile {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[3]")
      .appName("Synergy")
      .getOrCreate()
    val sc = spark.sparkContext
    val hdfsDate = new HdfsDemo()
    hdfsDate.createTwitterFile()
// Read CSV file into DataFrame.
    import spark.implicits._
  
    val simpleSchema = new StructType()
            .add("data", ArrayType(new StructType()
            .add("author_id", StringType)
            .add("id", StringType)
            .add("text", StringType)))
            
            
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
    
    val df_with_schema = spark.read.schema(simpleSchema).json(s"hdfs:///user/maria_dev/Twitter/twitter${hdfsDate.date}.json")
    val resultCount5 = df_with_schema.select(count($"data")).collect()(0)
    val resultCount6 = resultCount5(0).toString.toInt
    for (x <- 0 until resultCount6) {
    df_with_schema.select($"includes".getItem("users")(0)("name").as("username") , $"data".getItem(x)("text").as("tweet")).show(false)
    }
    hdfsDate.deleteFile()
  }
}
