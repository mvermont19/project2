package example

import misc._
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
  //this is the map that has all the crypto and the ids of the twitter pages to run through the requests in a for loop
  val cryptoMap = Map("ethereum" -> List("2312333412", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "bitcoin" -> List("361289499", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "solana" -> List("951329744804392960", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "ripple" -> List("1051053836", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "loopring" -> List("9130922", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "polkadot" -> List("1595615893", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "boba" -> List("831847934534746114", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "dogecoin" -> List("2235729541", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "tron" -> List("894231710065446912", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "terra" -> List("1022028994772910086", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "algorand" -> List("927909832002277376", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "neo" -> List("2592325530", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "litecoin" -> List("385562752", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"), "cosmos" -> List("15223775", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
  "polygon" -> List("914738730740715521", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"))

  //this is an empty date variable so I can use it in the createFile and deleteFile functions
  var date = ""
  //empty crypto variable so you can add it to the path when creating the twitter file
  var crypto = ""
  //create date formatter object to call the start date and end date functions
  val dateFormat = new DateFormatter()

  def twitterApi(url: String): String = {
    //create http connection and set it to get
    val httpClient = new DefaultHttpClient();
    val get = new HttpGet(url)
    //set header and add authorization token
    get.setHeader("Content-Type", "application/json")
    get.setHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAN6sVgEAAAAAMMjMMWrwgGyv7YQOWN%2FSAsO5SGM%3Dg8MG9Jq93Rlllaok6eht7HvRCruN4Vpzp4NaVsZaaHHWSTzKI8")
    val httpResponse = httpClient.execute(get)
    val entity = httpResponse.getEntity()
    var content = ""
    //if we get connection, then grab content from api
    if (entity != null) {
      val inputStream = entity.getContent()
      content = scala.io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager().shutdown()
    return content
  }

  def createTwitterFile (cryptoName: String, start: String, end: String, date: String): Unit = {
    //check to make sure the value being passed in is actually in the map
    if (cryptoMap.contains(cryptoName)) {
    crypto = cryptoName
    //date = scala.io.StdIn.readLine("What is the date you would like to search for? ")
    //the dateFormat function tries to parse the date you pass in in the correct format(yyyy-MM-dd), and if it can't, it will leave an empty date variable
    //then I do a check to see if it is empty, and if it is empty it will tell us it's not a valid format
    //val startDate = dateFormat.startDate(date)
    //val endDate = dateFormat.endDate(date)
    if (start.isEmpty()) {
        println("this is not a valid date format")
    } else {
      //loops through the crypto map value and does all the calls
    for (x <- 0 until 6){
    val twitterData = twitterApi(s"https://api.twitter.com/2/users/${cryptoMap(cryptoName)(x)}/tweets?start_time=$start&end_time=$end&expansions=author_id&user.fields=username,name")
    //adds all the json records to a file and puts it into HDFS
    createFile(twitterData, cryptoName, date)
    }
    }
  } else {
    println("this is not a valid crypto currency")
  }
}



  def createFile(json: String, crypto: String, date: String): Unit = {

    val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/Twitter/"
    //I use the date function to create unique files based on the date
    val filename = path + "twitter"+ crypto + date +".json"
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

  //same idea with this, checks to make sure there is a file with that name and deletes it if it exists
  def deleteFile(crypto: String, date: String): Unit = {
    val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/Twitter/"
    val filename = path + "twitter" + crypto + date +".json"

    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    val filepath = new Path(filename)
    val isExisting = fs.exists(filepath)

    if(isExisting) {
      println("Yes it does exist. Deleting it...")
      fs.delete(filepath, false)
    } else {
      println("please try again")
    }
  }
}