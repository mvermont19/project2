package app

import misc._

object `package` {
    val MAIN_MENU = "Main Menu\n 1: Scrape securities data\n 2: Quit"
    val SCRAPE_MENU = "Which type of security?\n 1: Stock\n 2: Cryptocurrency"
    val DATA_DIRECTORY = "data/"
    val SECURITIES_DB_PATH = s"${DATA_DIRECTORY}db.json"

    def scrape(security: Security) {

    }
}