package app

import misc._
import data.schema._
import data.api._
import com.github.nscala_time.time.Imports._
import java.io.File
import java.io.PrintWriter

object `package` {
    val MAIN_MENU = "Main Menu\n 1: Scrape securities data to disk\n 2: Load securities data from disk\n 3: Quit"
    val SCRAPE_MENU = "Scrape Security Data\nWhich type of security?\n 1: Stock\n 2: Cryptocurrency\n 3: Previous menu"
    val LOAD_MENU = "Entire database or individual securities?\n 1: Entire database\n 2: Individial securities\n 3: Previous menu"
    val DATA_DIRECTORY = "data/"
    val SECURITIES_DB_PATH = s"${DATA_DIRECTORY}db.json"

    def scrape(security: Security) {
        var articles = List[ArticleRecord]()
        NewsApi.scrapeArticles(security.name).foreach(x => articles = articles :+ ArticleRecord(DateTime.parse(x.publishedAt), x.title, 0.0f, x.description, x.content))

        println(articles)

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