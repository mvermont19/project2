package app

import misc._
import data.schema._
import data.api._
import com.github.nscala_time.time.Imports._
import java.io.File
import java.io.PrintWriter
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.NoTypeHints

object `package` {
    val APP_VERSION = "0.1.0"
    val DATA_DIRECTORY = "data/"
    val SECURITIES_DB_FILE = "db.json"

    implicit val formats = Serialization.formats(NoTypeHints)

    def scrape(security: Security) {
        var articles = List[ArticleRecord]()
        NewsApi.scrapeArticles(security.name).foreach(x => articles = articles :+ ArticleRecord(DateTime.parse(x.publishedAt), x.title, 0.0f, x.description, x.content))

        println(articles)

        val writer = new PrintWriter(new File(s"${DATA_DIRECTORY}${security.name}.json"))
        writer.write(write[List[ArticleRecord]](articles))
        writer.close()

        //1. Get results from APIs
        // a. AlphaVantage securities prices (past approx. 2 years, daily)
        // b. Twitter
        // c. NewsAPI headlines by topic/company/cryptocurrency/etc (past 30 days, daily)

        /*
        val apiResult = security match {
            case x: Stock => StockScraper.scrape(x, DAY)
            case x: Cryptocurrency => CryptocurrencyScraper.scrape(x, DAY)
        }

        println(apiResult)
        */

        //2. Collate datasets & strip-out unnecessary fields... Converting to ScrapeDb should match DB data model
        // a. For each day in the securities timeseries, 
    }

    /*
    def writeToDb(dayRecord: DayRecord) {}
    def writeToJson(dayRecord: DayRecord) {}
    def writeToJson(securityRecord: SecurityRecord) {}
    */

    def scrapeToDb(security: Security) {
        scrape(security)
        //TODO: Convert raw scraped results to local db schema
    }

    def scrapeToFile(security: Security) {
        //TODO
    }

    /*
    def loadSecuritiesDb(path: String): SecuritiesDb {
        //TODO
        SecuritiesDb()
    }

    def loadSecurityFile(path: String): SecurityRecord {
        //TODO
        SecurityRecord()
    }
    */
}