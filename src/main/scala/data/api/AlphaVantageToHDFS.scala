package data.api

import scala.util.Try
import java.io.IOException

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path

import scala.io.Source
import java.io.PrintWriter
import java.io.File

class AlphaVantageToHDFS {
    

    var coinSymbol = ""
    var currencySymbol = ""

    def getCryptoFiles(): Unit = {
        val cyrptoSequence: Seq[String] = Seq("BTC", "ETH", "SOL", "XRP", "LRC", "DOT", "OMG", "DOGE", "TRX", "LUNA", "ALGO", "NEO", "LTC", "ATOM", "MATIC")
        val currencies: Seq[String] = Seq("CNY", "EUR", "GBP")
        for(x <- cyrptoSequence){
            for(y <- currencies){
                coinSymbol = x
                currencySymbol = y
                Thread.sleep(15000)
                getApiData(x, y)
            }
        }
    }

    def getApiData(coinSymbol: String, currencySymbol: String): Unit = {
        val url = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=" + coinSymbol + "&market="+ currencySymbol + "&datatype=csv&apikey=4TZZOJKGYN1WMDJ9"
        
        val result = scala.io.Source.fromURL(url).mkString
        println("Retrieving Data from API Pipeline...")

        createFile(result, currencySymbol, coinSymbol)
    }
    

    def createFile(json: String, currency: String, coin: String): Unit = {

    val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/HDFSalphaVantageFiles/"
    //I use the date function to create unique files based on the date
    val filename = path + currency +"_" + coin + ".csv"
    println(s"Creating file $filename ...")
    
    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    
    // Check if file exists. If yes, delete it.
    println("Checking if it already exists...")
    val filepath = new Path( filename)
    val isExisting = fs.exists(filepath)
    //checks to make sure the file exists before it appends
    if(isExisting) {
      println("yes it does, appending it")
      val appender = fs.append(filepath)
      val newWriter = new PrintWriter(appender)
      //adds a new line and appends the json so it loads into RDD
      newWriter.write("\n" + json)
      newWriter.close()    
    } else {
    //if it doesn't exist it creates new file
    val output = fs.create(new Path(filename))
    
    val writer = new PrintWriter(output)
    writer.write(json)
    writer.close()
    
    println(s"Done creating file $filename ...")
  }
  }
}