package app

import org.joda.time.Interval

object AlphaVantage {
    val apiKey = System.getenv(app.ALPHAVANTAGE_API_KEY_KEY)

    case class StockRecord(date: String, open: Float, high: Float, low: Float, close: Float, volume: Float)

    //DUNNO: valid date formats?
    case class CryptoRecord(date: String, openCny: Float, highCny: Float, lowCny: Float, closeCny: Float, openUsd: Float, highUsd: Float, lowUsd: Float, closeUsd: Float, volume: Float, marketCap: Float)

    class StockScraper extends Scraper {
        protected override def scrape(query: String): String = {
            scala.io.Source.fromURL(query).mkString
        }

        def scrape(stock: Stock, timePeriod: TimePeriod): String = {
            var function = ""
            
            timePeriod match {
                case Day => function = "TIME_SERIES_DAILY"
                
                case Week => function = "TIME_SERIES_WEEKLY"
                
                case Month => function = "TIME_SERIES_MONTHLY"
            }
            
            scrape(s"https://www.alphavantage.co/query?function=${function}&symbol=${stock.symbol}&market=USD&datatype=csv&apikey=${apiKey}")
        }
    }

    class CryptoScraper extends Scraper {
        protected override def scrape(query: String): String = {
            scala.io.Source.fromURL(query).mkString
        }

        def scrape(stock: Stock, timePeriod: TimePeriod): String = {
            var function = ""
            
            timePeriod match {
                case Day => function = "DIGITAL_CURRENCY_DAILY"
                
                case Week => function = "DIGITAL_CURRENCY_WEEKLY"
                
                case Month => function = "DIGITAL_CURRENCY_MONTHLY"
            }
            
            scrape(s"https://www.alphavantage.co/query?function=${function}&symbol=${stock.symbol}&market=USD&datatype=csv&apikey=${apiKey}")
        }
    }
}