package data.schema

import misc._
import com.github.nscala_time.time.Imports._

case object SecurityKindEnum {
  val Stock = 1
  val Currency = 2
  val Cryptocurrency = 3
}

case class SecurityRecord(name: String, symbol: String, kind: Int, var priceHistory: List[SecurityTimeseriesRecord], var relevantArticles: List[ArticleRecord], var relevantTweets: List[TweetRecord])

case class SecurityTimeseriesRecord(date: String, open: Float, low: Float, high: Float, close: Float, volume: Int, marketCap: Float)