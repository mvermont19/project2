package app

import misc._
import data.schema._
import data.api._
import com.github.nscala_time.time.Imports._
import org.apache.spark.{SparkContext, SparkConf}
import java.nio.file.{Paths, Files}
import java.util.Arrays
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.NoTypeHints

object `package` {
	implicit val formats = Serialization.formats(NoTypeHints)

	val APP_NAME = "project2"
	val APP_VERSION = "0.1.0"
	val DATA_DIRECTORY = "data/"
	val SECURITIES_DB_FILE = "db.json"
//	val sparkConf = new SparkConf().setAppName(APP_NAME)

	var securitiesDb = SecuritiesDb()
	/*
	var sparkContext: Option[SparkContext] = None
	//var spark: Option[SparkSession] = None

	private def initializeSpark() {
		//(sc, spark) match {
		sparkContext match {
			//case (None, None) => {
			case None => {
				//Try to connect to Spark instance
				sparkContext = Some(new SparkContext(sparkConf))
			}

			case _ => throw new Exception("Spark is already initialized")
		}
	}
	*/

	//Scrape = Fetch + SaveToDisk((DirectoryDb, JsonDb), InsertOrOverwrite)
	//Load = NukeDataFrame + LoadToDataFrame(JsonDb)
	def scrape(security: Security) {
		//{Begin by retrieving API results and dumping to disk

		//1. Get results from API endpoints
		// a. AlphaVantage securities prices (past approx. 2 years, daily)
		// b. NewsAPI headlines by topic/company/cryptocurrency/etc (past 30 days, daily)
		// c. Run Google NLP sentiment analysis against articles headlines?
		// d. Twitter?

		//{AlphaVantage
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
		})
		Files.write(Paths.get(s"${DATA_DIRECTORY}${security.name}.csv"), responseString.getBytes())
		Files.write(Paths.get(s"${DATA_DIRECTORY}${security.name}.json"), write(recordsList).getBytes())
		//}

		//{NewsAPI
		//TODO: NewsApi.scrapeArticles(security.name)
		//}

		//}
		
		//{Now, translate API results into local schema records (SecurityRecord) and collate into local db

		//2. Collate datasets & strip-out unnecessary fields... Converting to ScrapeDb should match DB data model
		// a. For each day in the securities timeseries, ...

		securitiesDb.securities = securitiesDb.securities :+ SecurityRecord(security.name, security.symbol, security match {
			case x: Stock => SecurityKindEnum.Stock
			case x: Cryptocurrency => SecurityKindEnum.Cryptocurrency
		}, List[SecurityTimeseriesRecord](), List[ArticleRecord](), List[TweetRecord]())

		Files.write(Paths.get(s"${DATA_DIRECTORY}${SECURITIES_DB_FILE}"), write(securitiesDb).getBytes())

		//}
	}

	def scrapeToDb(security: Security) {
		scrape(security)
		//TODO: Convert raw scraped results to local db schema
	}

	def scrapeToFile(security: Security) {
		//TODO
	}

	def loadSecuritiesDb(): SecuritiesDb = {
		read[SecuritiesDb](new String(Files.readAllBytes(Paths.get(s"${DATA_DIRECTORY}${SECURITIES_DB_FILE}"))))
	}

	def loadSecurityRecord(): SecurityRecord = {
		SecurityRecord("Foo", "FOO", SecurityKindEnum.Stock, List[SecurityTimeseriesRecord](), List[ArticleRecord](), List[TweetRecord]())
	}

	/*
	add(SecurityRecord(security.name, security.symbol, security match {
		case x: Stock => SecurityKindEnum.Stock
		case x: Cryptocurrency => SecurityKindEnum.Cryptocurrency
	}, List[SecurityTimeseriesRecord](), List[ArticleRecord](), List[TweetRecord]()))
	*/
	/*
	def writeToDb(dayRecord: DayRecord) {}
	def writeToJson(dayRecord: DayRecord) {}
	def writeToJson(securityRecord: SecurityRecord) {}
	*/
	//def loadToDataFrame(path: String): DataFrame = {}
	/*
		def loadCompanyRecord(company: String) {
			val sc = new SparkContext()
			val companyObject = sc.wholeTextFiles(s"data/${company}.json")
			println(companyObject)
			/*
			val df = sc.read.json(companyObject)
			df.printSchema()
			df.show(false)
			*/
		}
	*/
}