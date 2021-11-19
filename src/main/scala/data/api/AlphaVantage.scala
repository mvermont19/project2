package data.api

import data.api._
import data.schema._
import data._
import com.github.nscala_time.time.Imports._

import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.NoTypeHints

object AlphaVantage {
    implicit val formats = Serialization.formats(NoTypeHints)

    val apiKey = Option(System.getenv(ALPHAVANTAGE_API_KEY_ENV)) match {
        case Some(key) => key
        case None => ALPHAVANTAGE_API_DEFAULT_KEY
    }

    case class StockRecord(date: String, open: Float, high: Float, low: Float, close: Float, volume: Float)

    case class CryptocurrencyRecord(date: String, openCny: Float, highCny: Float, lowCny: Float, closeCny: Float, openUsd: Float, highUsd: Float, lowUsd: Float, closeUsd: Float, volume: Float, marketCap: Float)

    object StockScraper extends Scraper {
        def scrape(stock: Stock, currency: Option[String], timePeriod: TimePeriod = Day): String = {
            var function = ""
            
            timePeriod match {
                case Day => function = "TIME_SERIES_DAILY"
                case Week => function = "TIME_SERIES_WEEKLY"
                case Month => function = "TIME_SERIES_MONTHLY"
            }
            
            scrape(s"https://www.alphavantage.co/query?function=$function&symbol=${stock.symbol}${
                currency match {
                    case Some(x) => s"&market=$x"
                    case None =>
                }
            }&datatype=csv&apikey=$apiKey")
        }
    }

    object CryptocurrencyScraper extends Scraper {    
        def scrape(cryptocurrency: Cryptocurrency, currency: Option[String] = None, timePeriod: TimePeriod = Day): String = {
            var function = ""
            
            timePeriod match {
                case Day => function = "DIGITAL_CURRENCY_DAILY"
                case Week => function = "DIGITAL_CURRENCY_WEEKLY"
                case Month => function = "DIGITAL_CURRENCY_MONTHLY"
            }
            
            scrape(s"https://www.alphavantage.co/query?function=$function&symbol=${cryptocurrency.symbol}${
                currency match {
                    case Some(x) => s"&market=$x"
                    case None =>
                }
            }&datatype=csv&apikey=$apiKey")
        }
    }
}