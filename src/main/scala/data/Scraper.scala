package data

/**
 * An object that knows how to scrape results from a source
*/
trait Scraper {
    /**
     * @param query A URL to an API endpoint to retrieve results from
     * @return The result set encoded as a String
    */
    protected def scrape(query: String): String = {
        scala.io.Source.fromURL(query).mkString
    }
}