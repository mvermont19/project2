package api

abstract class Scraper {
    protected def scrape(query: String): String
}