package data.api

import data.api._
import data.schema._
import com.github.nscala_time.time.Imports._
import org.joda.time.Days

//DUNNO: Can spark replace these?
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.NoTypeHints

object NewsApi {
  implicit val formats = Serialization.formats(NoTypeHints)

  val apiKey = Option(System.getenv(NEWS_API_KEY_ENV)) match {
    case Some(key) => key
    case None => NEWS_API_DEFAULT_KEY
  }

  val SCRAPE_INTERVAL = 2.days
  val MAX_PAGES = 2

  /**{
   * NewsAPI data models
  */
  case class Response(
    status: String,
    totalResults: Int,
    articles: List[Article]
  )

  case class Article(
    source: Source,
    author: String,
    title: String,
    description: String,
    url: String,
    urlToImage: Option[String],
    publishedAt: String,
    content: String
  )

  case class Source(
    id: Option[String],
    name: String
  )
  //}

  def scrapeArticles(topic: String): List[Article] = {
    //NewsAPI free plan allows you to pull headlines from up to a month ago, so loop through each day since then and pull headlines
    var result = List[Article]()
    var cursor = DateTime.now()
    val endDate = cursor - SCRAPE_INTERVAL

    var totalResultsCount = 0
    var totalMaximumResults = 0

    println(s"Scraping news headlines for topic '$topic'...")
    //Loop per day
    do {
      val year = cursor.year().get()
      val month = cursor.monthOfYear().get()
      val day = cursor.dayOfMonth().get()

      var dailyArticles = List[Article]()
      var dailyResultsCount = 0
      var dailyResultsPage = 1
      var dailyMaximumResults = 0

      //Loop per results page (up to limit)
      do {
        val requestUrl = s"https://newsapi.org/v2/everything?q=${topic}&from=${year}-${month}-${day}&to=${year}-${month}-${day}&page=${dailyResultsPage}&sortBy=publishedAt&apiKey=${apiKey}"
        val responseJson = scala.io.Source.fromURL(requestUrl).mkString
        val responseObject = read[Response](responseJson)

        dailyMaximumResults = responseObject.totalResults

        dailyArticles = dailyArticles ::: responseObject.articles
        dailyResultsCount += responseObject.articles.length
        
        println(s"Received results page #${dailyResultsPage} for ${month}/${day}/${year}, found articles ${dailyResultsCount - responseObject.articles.length + 1} through ${dailyResultsCount}...")
        
        dailyResultsPage += 1

        //TODO: Better throttling
        Thread.sleep(REQUEST_THROTTLE)

        //Loop through pages until reading all results or reaching page limit
      } while(dailyResultsCount < dailyMaximumResults && dailyResultsPage <= MAX_PAGES)

      totalResultsCount += dailyResultsCount
      totalMaximumResults += dailyMaximumResults

      result = result ::: dailyArticles

      cursor = cursor - 1.days
    } while(Math.abs(Days.daysBetween(cursor, endDate).getDays) > 0)

    println(s"\nJob complete!\n\nScraped ${totalResultsCount} of ${totalMaximumResults} headlines pertaining to topic '${topic}'\n\nPress Enter to continue")
    readLine()
    print("\u001b[2J")

    result
  }
}