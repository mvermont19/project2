package app

import app._
import misc._
import scala.io.StdIn.readLine

object Cli extends App {
  var state = 0
  var menu = MAIN_MENU

  print("\u001b[2J")

  do {
    print(s"${menu + "\n\nOption: "}")

    state match {
      //Main menu = 0
      case 0 => {
        readLine() match {
          //Transition to Scrape menu
          case "1" => {
            state = 1
            menu = SCRAPE_MENU
          }
          //Exit app
          case "2" => state = -1
          //Do nothing (bad input)
          case _ => {}
        }
      }

      //API scraping menu = 1
      case 1 => {
        readLine() match {
          //Stock
          case "1" => {
            print("Company: ")
            val name = readLine()
            print("Ticker symbol: ")
            val symbol = readLine()
            scrape(Stock(name, symbol))
          }
          //Cryptocurrency
          case "2" => {
            print("Cryptocurrency: ")
            val name = readLine()
            print("Ticker symbol: ")
            val symbol = readLine()
            scrape(Cryptocurrency(name, symbol))
          }
          //Previous menu
          case "3" => {
            state = 0
            menu = MAIN_MENU
          }
          //Do nothing (bad input)
          case _ => {}
        }
      }
    }

    print("\u001b[2J")
  } while(state != -1)
}