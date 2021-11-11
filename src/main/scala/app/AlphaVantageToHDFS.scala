package app

import scala.util.Try
import java.io.IOException

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path

import scala.io.Source
import java.io.PrintWriter
import java.io.File

object AlphaVantageToHDFS {
    
    def main(args: Array[String]) {
        getCryptoFiles
    }

    var coinSymbol = ""
    var timeFrame = ""

    def getCryptoFiles {
        val cyrptoSequence: Seq[String] = Seq("BTC", "ETH", "SOL", "XRP", "LRC", "DOT", "OMG", "DOGE", "TRX", "LUNA", "ALGO", "NEO", "LTC", "ATOM", "MATIC")
        val timeSequence: Seq[String] = Seq("DIGITAL_CURRENCY_DAILY", "DIGITAL_CURRENCY_WEEKLY", "DIGITAL_CURRENCY_MONTHLY")

        for(x <- cyrptoSequence){
            for(y <- timeSequence){
                coinSymbol = x
                timeFrame = y
                getApiData(x, y)
            }
        }
    }

    def getApiData(coinSymbol: String, timeFrame: String) {
        val url = "https://www.alphavantage.co/query?function=" + timeFrame + "&symbol=" + coinSymbol + "&market=USD&datatype=csv&apikey=4TZZOJKGYN1WMDJ9"
        
        val result = scala.io.Source.fromURL(url).mkString
        println("Retrieving Data from API Pipeline...")

        moveDataToHDP(result)
        copyFromLocal()
    }
    
    def moveDataToHDP(csv: String): Unit = {
        val filePathHDP = "/home/maria_dev/AlphaVantageFiles/" + timeFrame + "_" + coinSymbol + ".csv"
        val writer = new PrintWriter(new File(filePathHDP))
        writer.write(csv)
        writer.close()

        println("File created in HDP Path: " + filePathHDP)
    }

    val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/HDFSalphaVantageFiles/"

    def copyFromLocal(): Unit = {
        val src = "file:///home/maria_dev/AlphaVantageFiles/" + timeFrame + "_" + coinSymbol + ".csv"
        val target = path + timeFrame + "_" + coinSymbol + ".csv"
        println(s"Copying local file $src to $target ...")
        
        val conf = new Configuration()
        val fs = FileSystem.get(conf)

        val localpath = new Path(src)
        val hdfspath = new Path(target)
        
        fs.copyFromLocalFile(false, localpath, hdfspath)
        println(s"Done copying local file $src to $target ...")
    }
}