// readcsvdf.scala or readcsv.scala
package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import scala.runtime.LongRef
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions.lit
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.Dataset
import scala.collection.mutable.ArrayBuffer

class AllToDF {

var all = spark.createDataFrame(spark.sparkContext.emptyRDD[Row], new StructType()
    .add("timestamp", StringType, true)
      .add("ForeignOpen", DoubleType , true)
      .add("ForeignHigh", DoubleType, true)
      .add("ForeignLow", DoubleType, true)
      .add("ForeignClose", DoubleType, true)
      .add("USOpen", DoubleType, true)
      .add("USHigh", DoubleType, true)
      .add("USLow", DoubleType, true)
      .add("USClose", DoubleType, true)
      .add("Volume", DoubleType, true)
      .add("MarketCap", DoubleType, true)
      .add("CryptoCurrency", StringType, true)
      .add("ForeignCurrency", StringType, true))

  def loadCryptoData() = {
    val cryptoSequence: Seq[String] = Seq("BTC", "ETH", "SOL", "XRP", "LRC", "DOT", "OMG", "DOGE", "TRX", "LUNA", "ALGO", "NEO", "LTC", "ATOM", "MATIC")
    

    for (x <- 0 until cryptoSequence.length) {
        var cnyDF = showData(cryptoSequence(x), "CNY")
        var eurDF = showData(cryptoSequence(x), "EUR")
        var gbpDF = showData(cryptoSequence(x), "GBP")
        var df = cnyDF.union(eurDF).union(gbpDF)
        all = all.union(df)
      }
    }
  //this will read the twitter file, load into DF, and display it
  def showTweets (crypto: String, start: String, end: String, date: String)= {
    
    //create TwitterToHDFS object to call the createTwitterFile function
    val twitterFile = new TwitterToHDFS()
    twitterFile.createTwitterFile(crypto, start, end, date)


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
    val filename = (s"hdfs:///user/maria_dev/Twitter/twitter${crypto}${date}.json")
    val filepath = new Path(filename)
    val isExisting = fs.exists(filepath)
    if(isExisting) {
    //display the username and tweets from the twitter json file
    val df_with_schema = spark.read.schema(simpleSchema).json(s"hdfs:///user/maria_dev/Twitter/twitter${crypto}${date}.json")
    val newCount = df_with_schema.agg(max($"meta")("result_count")).collect()(0)
    val newCount2 = newCount(0).toString.toInt
    for (x <- 0 until newCount2) {
    df_with_schema.select($"includes".getItem("users")(0)("name").as("username"), $"data".getItem(x)("text").as("tweet")).na.drop().show(false) 
  } 
    //deletes it when done so there isn't a ton of files created when you're done
    //can be taken out if you want to keep the files however
    twitterFile.deleteFile(crypto, date)
  } else {
    println("please try again")
  }
}

def showData(crypto: String, currency: String): DataFrame = {
    //val alphaFile = new AlphaVantageToHDFS()
    //aplhaFile.getCryptoFiles()
    val df_with_schema = spark.read
      .options(Map("header" -> "true", "inferSchema" -> "true", "delimiter" -> ","))
      .csv("hdfs:///user/maria_dev/HDFSalphaVantageFiles/" + currency + "_" + crypto + ".csv")
    df_with_schema.printSchema()
    val new_df = df_with_schema.withColumn("CryptoCurrency Name", lit(crypto)).withColumn("Currency", lit(currency))
    //defining the schema of the DF
    new_df
}


}

