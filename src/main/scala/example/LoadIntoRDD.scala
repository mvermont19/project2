// readcsvdf.scala or readcsv.scala
package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import scala.runtime.LongRef
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path

class TwitterToRDD {
  def showTweets (crypto: String)= {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[3]")
      .appName("Synergy")
      .getOrCreate()
    val sc = spark.sparkContext
    val twitterFile = new TwitterToHDFS()
    twitterFile.createTwitterFile(crypto)
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


    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    val filename = (s"hdfs:///user/maria_dev/Twitter/twitter${twitterFile.date}.json")
    val filepath = new Path( filename)
    val isExisting = fs.exists(filepath)
    if(isExisting) {
    val df_with_schema = spark.read.schema(simpleSchema).json(s"hdfs:///user/maria_dev/Twitter/twitter${twitterFile.date}.json")
    val resultCount5 = df_with_schema.select(count($"data")).collect()(0)
    val resultCount6 = resultCount5(0).toString.toInt
    for (x <- 0 until resultCount6) {
    df_with_schema.select($"includes".getItem("users")(0)("name").as("username") , $"data".getItem(x)("text").as("tweet")).na.drop().show(false) 
  } 

    twitterFile.deleteFile()
  } else {
    println("please try again")
  }
}
}