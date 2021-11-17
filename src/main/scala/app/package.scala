package app

import data.schema._
import data.api._
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

object `package` {
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
	
	def initializeSpark() {
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

	def scrape(security: Security) {
		//{Begin by retrieving API results and dumping to disk

		//Get results from API endpoints
		// a. AlphaVantage securities prices (past approx. 2 years, daily)
		// b. NewsAPI headlines by topic/company/cryptocurrency/etc (past 30 days, daily)
		// c. Run Google NLP sentiment analysis against articles headlines?
		// d. Twitter?

		var timeseries = List[SecurityTimeseriesRecord]()
		var articles = List[ArticleRecord]()
		var tweets = List[TweetRecord]()

		//{AlphaVantage
		{
			var responseString = ""
			security match {
				case x: Stock => responseString = AlphaVantage.StockScraper.scrape(security.asInstanceOf[Stock])
				case x: Cryptocurrency => responseString = AlphaVantage.CryptocurrencyScraper.scrape(security.asInstanceOf[Cryptocurrency])
			}
			//Strip the first line (column headers) before parsing object
			var rows = responseString.split("\n")
			rows = Arrays.copyOfRange(rows, 1, rows.length)
			var recordsList = security match {
				case x: Stock => List[AlphaVantage.StockRecord]()
				case x: Cryptocurrency => List[AlphaVantage.CryptocurrencyRecord]()
			}
			rows.foreach((row) => {
				val columns = row.split(",")
				security match {
					case x: Stock => recordsList = recordsList :+ AlphaVantage.StockRecord(columns(0), columns(1).toFloat, columns(2).toFloat, columns(3).toFloat, columns(4).toFloat, columns(5).toFloat)
					case x: Cryptocurrency => recordsList = recordsList :+ AlphaVantage.CryptocurrencyRecord(columns(0), columns(1).toFloat, columns(2).toFloat, columns(3).toFloat, columns(4).toFloat, columns(5).toFloat, columns(6).toFloat, columns(7).toFloat, columns(8).toFloat, columns(9).toFloat, columns(10).toFloat)
				}
				timeseries = timeseries :+ SecurityTimeseriesRecord(columns(0), columns(1).toFloat, columns(2).toFloat, columns(3).toFloat, columns(4).toFloat, 0, 0.0f)
			})
			Files.write(Paths.get(s"$DATA_DIRECTORY${security.name}.csv"), responseString.getBytes())
			Files.write(Paths.get(s"$DATA_DIRECTORY${security.name}.json"), write(recordsList).getBytes())
		}

		println(s"\nScraped ${timeseries.length} timeseries records for security ${security.name}...")
		//}

		//{NewsAPI
		NewsApi.scrapeArticles(security.name).foreach((article) => {
			articles = articles :+ ArticleRecord(article.publishedAt, article.title, 0.0f, article.description, article.content)
		})
		//}

		//{Twitter
		//TODO
		//}

		//{Google
		//MAYBE
		//}

		//}

		
		//{Now, translate API results into local schema records (SecurityRecord) and collate into local db

		securitiesDb.securities = securitiesDb.securities.filter((x) => {
			var result = true

			if(x.name == security.name) {
				println(s"Removing existing record for security '${security.name}'\n\n$PRESS_ENTER")
				readLine()
				result = false
			} 

			result
		})

		securitiesDb.securities = securitiesDb.securities :+ SecurityRecord(security.name, security.symbol, security match {
			case x: Stock => SecurityKindEnum.Stock
			case x: Cryptocurrency => SecurityKindEnum.Cryptocurrency
		}, timeseries, articles, tweets)

		Files.write(Paths.get(s"$DATA_DIRECTORY$SECURITIES_DB_FILE"), write(securitiesDb).getBytes())

		//}
	}

	def load(): DataFrame = {
		securitiesDb = loadSecuritiesDb(Paths.get(s"$DATA_DIRECTORY$SECURITIES_DB_FILE"))
		sparkSession match {
			case Some(_) =>
			case None => initializeSpark()
		}

		//{Copy database file from local file system to HDFS
		val hdfsPath = s"/$DATA_DIRECTORY$SECURITIES_DB_FILE"
		val localPath = s"${Paths.get("").toAbsolutePath.toString}/$DATA_DIRECTORY$SECURITIES_DB_FILE"
		val fs = FileSystem.get(new Configuration())

		println(s"Copying file://$localPath to hdfs://$hdfsPath...")
		fs.copyFromLocalFile(false, new HdpPath(localPath), new HdpPath(hdfsPath))
		//}

		val df: DataFrame = sparkSession.get.read.json(hdfsPath)
		println("\nDatabase schema:\n")
		df.printSchema()
		print(PRESS_ENTER)
		readLine()

		println("Securities list:\n")
		sparkSession.get.sqlContext.sql(s"CREATE TEMPORARY VIEW securities USING json OPTIONS (path '$hdfsPath')")
		sparkSession.get.sqlContext.sql("DESCRIBE securities").show(false)
		print(PRESS_ENTER)
		readLine()
		df
	}

	def loadSecuritiesDb(): SecuritiesDb = {
		read[SecuritiesDb](new String(Files.readAllBytes(Paths.get(s"$DATA_DIRECTORY$SECURITIES_DB_FILE"))))
		sparkSession.get.read.json(hdfsPath)
	}

	def loadSecuritiesDb(path: Path): SecuritiesDb = {
		read[SecuritiesDb](new String(Files.readAllBytes(path)))
	}
}