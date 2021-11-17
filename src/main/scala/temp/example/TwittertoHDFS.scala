package example

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path

import java.time.{Instant, LocalDate}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import scala.util.Try
import scala.util.Failure

import scala.io.Source
import java.io.PrintWriter
import java.io.File
import scala.io._

class TwitterToHDFS {
  
  val cryptoMap = Map("ethereum" -> List("2312333412", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "bitcoin" -> List("361289499", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "solana" -> List("951329744804392960", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "ripple" -> List("1051053836", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "loopring" -> List("9130922", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "polkadot" -> List("1595615893", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "boba" -> List("831847934534746114", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "dogecoin" -> List("2235729541", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "tron" -> List("894231710065446912", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "terra" -> List("1022028994772910086", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "algorand" -> List("927909832002277376", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "neo" -> List("2592325530", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "litecoin" -> List("385562752", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "cosmos" -> List("15223775", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "polygon" -> List("914738730740715521", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"))

  var date = ""
  val twitter = new twitterAPI()
  val dateFormat = new DateFormatter()


  def createTwitterFile (crypto: String): Unit = {
    if (cryptoMap.contains(crypto)) {
    date = scala.io.StdIn.readLine("What is the date you would like to search for?")
    val startDate = dateFormat.startDate(date)
    val endDate = dateFormat.endDate(date)
    if (startDate.isEmpty()) {
        println("this is not a valid date format")
    } else {
    for (x <- 0 until 6){
    val twitterData = twitter.twitterApi(s"https://api.twitter.com/2/users/${cryptoMap(crypto)(x)}/tweets?start_time=$startDate&end_time=$endDate&expansions=author_id&user.fields=username,name")
    createFile(twitterData)
    }
    }
  } else {
    println("this is not a valid crypto currency")
  }
}
  


  def createFile(json: String): Unit = {

    val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/Twitter/"
    val filename = path + "twitter" + date +".json"
    println(s"Creating file $filename ...")
    
    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    
    // Check if file exists. If yes, delete it.
    println("Checking if it already exists...")
    val filepath = new Path( filename)
    val isExisting = fs.exists(filepath)
    if(isExisting) {
      println("yes it does, appending it")
      val appender = fs.append(filepath)
      val newWriter = new PrintWriter(appender)
      newWriter.write("\n" + json)
      newWriter.close()    
    } else {

    val output = fs.create(new Path(filename))
    
    val writer = new PrintWriter(output)
    writer.write(json)
    writer.close()
    
    println(s"Done creating file $filename ...")
  }
  }

  def deleteFile(): Unit = {
    val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/Twitter/"
    val filename = path + "twitter" + date +".json"

    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    val filepath = new Path( filename)
    val isExisting = fs.exists(filepath)

    if(isExisting) {
      println("Yes it does exist. Deleting it...")
      fs.delete(filepath, false)
    } else {
      println("there is no file with this name")
    }
  }
}