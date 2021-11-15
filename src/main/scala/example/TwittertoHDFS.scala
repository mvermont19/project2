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

/*object HdfsDemo {
  
  val twitter = new twitterAPI()
  val dateFormat = new DateFormatter()
  val twitterPath = "https://api.twitter.com/2/users/2312333412/tweets?start_time=&end_time=&user.fields=created_at,name"
  def main(args: Array[String]): Unit = {
    val date = scala.io.StdIn.readLine("What is the date you would like to search for?")
    val startDate = dateFormat.startDate(date)
    val endDate = dateFormat.endDate(date)
    if (startDate.isEmpty()) {
        println("this is not a valid date format")
    } else {
    val twitterData = twitter.twitterApi(s"https://api.twitter.com/2/users/2312333412/tweets?start_time=$startDate&end_time=$endDate&expansions=author_id&user.fields=username,name")
    createFile(twitterData)
    }
  }
  val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/Twitter/"


  def createFile(json: String): Unit = {
    val filename = path + "twitter.json"
    println(s"Creating file $filename ...")
    
    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    
    // Check if file exists. If yes, delete it.
    println("Checking if it already exists...")
    val filepath = new Path( filename)
    val isExisting = fs.exists(filepath)
    if(isExisting) {
      println("Yes it does exist. Deleting it...")
      fs.delete(filepath, false)
    }

    val output = fs.create(new Path(filename))
    
    val writer = new PrintWriter(output)
    writer.write(json)
    writer.close()
    
    println(s"Done creating file $filename ...")
  }
}*/