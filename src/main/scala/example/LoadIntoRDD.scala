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

class TwitterToDF {
  //this will read the twitter file, load into DF, and display it
  def showTweets (crypto: String)= {
    //create spark session
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[3]")
      .appName("Synergy")
      .getOrCreate()
    val sc = spark.sparkContext
    //create TwitterToHDFS object to call the createTwitterFile function
    val twitterFile = new TwitterToHDFS()
    twitterFile.createTwitterFile(crypto)
    import spark.implicits._

    //defining the schema of the DF
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

    //checking to make sure file exists before trying to load into DF
    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    val filename = (s"hdfs:///user/maria_dev/Twitter/twitter${twitterFile.crypto}${twitterFile.date}.json")
    val filepath = new Path( filename)
    val isExisting = fs.exists(filepath)
    if(isExisting) {
    //display the username and tweets from the twitter json file
    val df_with_schema = spark.read.schema(simpleSchema).json(s"hdfs:///user/maria_dev/Twitter/twitter${twitterFile.crypto}${twitterFile.date}.json")
    val resultCount5 = df_with_schema.select(count($"data")).collect()(0)
    val resultCount6 = resultCount5(0).toString.toInt
    for (x <- 0 until resultCount6) {
    df_with_schema.select($"includes".getItem("users")(0)("name").as("username") , $"data".getItem(x)("text").as("tweet")).na.drop().show(false) 
  } 
    //deletes it when done so there isn't a ton of files created when you're done
    //can be taken out if you want to keep the files however
    twitterFile.deleteFile()
  } else {
    println("please try again")
  }
}
}