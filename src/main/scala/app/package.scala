package app

import misc._
import data.schema._
import data.api._
import data.analysis._
import com.github.nscala_time.time.Imports._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path => HdpPath}
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import java.nio.file.{Path, Paths, Files}
import java.util.Arrays
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.NoTypeHints
import org.apache.log4j.{Level, Logger}

class LoadData {
	implicit val formats = Serialization.formats(NoTypeHints)

	val APP_NAME = "project2"
	val APP_VERSION = "0.1.0"
	val DATA_DIRECTORY = "data/"
	val SECURITIES_DB_NAME_ENV = "SECURITIES_DB_NAME"
	val SECURITIES_DB_FILE = (Option(System.getenv(SECURITIES_DB_NAME_ENV)) match {
		case Some(x) => x
		case None => "db"
	}) + ".json"
	val PRESS_ENTER = "Press Enter to continue"
	
	val rootLogger = Logger.getRootLogger()
	rootLogger.setLevel(Level.ERROR)
	
	Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
	Logger.getLogger("org.spark-project").setLevel(Level.ERROR)

	var securitiesDb = SecuritiesDb()
	var sparkConf = new SparkConf().setAppName(APP_NAME)
	var sparkContext: Option[SparkContext] = None
	var sparkSession: Option[SparkSession] = None
	var securitiesDf: Option[DataFrame] = None
	
	def initializeSpark(): Unit = {
		(sparkContext, sparkSession) match {
			case (None, None) => {
				print("Attempting to connect to Spark instance...")
				sparkContext = Some(new SparkContext(sparkConf))
				sparkSession = Some(SparkSession.builder().getOrCreate())
				println("Success!")
			}

			case _ => throw new Exception("Spark is already initialized")
		}
	}

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
  //create the twitter object so I can call the twitterApi function
  val twitter = new twitterAPI()
  //create date formatter object to call the start date and end date functions

  def createTwitterFile (cryptoName: String, start: String, end: String, date: String): Unit = {
    //check to make sure the value being passed in is actually in the map
    if (cryptoMap.contains(cryptoName)) {
    crypto = cryptoName
    //date = scala.io.StdIn.readLine("What is the date you would like to search for? ")
    //the dateFormat function tries to parse the date you pass in in the correct format(yyyy-MM-dd), and if it can't, it will leave an empty date variable
    //then I do a check to see if it is empty, and if it is empty it will tell us it's not a valid format
    val startDate = DateFormatter.startDate(date)
    val endDate = DateFormatter.endDate(date)
    if (start.isEmpty()) {
        println("this is not a valid date format")
    } else {
      //loops through the crypto map value and does all the calls
    for (x <- 0 until 6){
      //+-val twitterData = Twitter.scrapeByUserId(cryptoMap(cryptoName)(x))
    val twitterData = twitter.twitterApi(s"https://api.twitter.com/2/users/${cryptoMap(cryptoName)(x)}/tweets?start_time=$start&end_time=$end&expansions=author_id&user.fields=username,name")
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
