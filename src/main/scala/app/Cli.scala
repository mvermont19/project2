package app

import app._
import misc._
import data.api._
import scala.io.StdIn.readLine
import java.nio.file.{Paths, Files}
import java.util.Arrays

import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.NoTypeHints

object Cli extends App {
  implicit val formats = Serialization.formats(NoTypeHints)

  val SPLASH_MESSAGE = s"Revature Project 2 - Cryptocurrency Analysis w/ Spark v${APP_VERSION}\n"
  val CLEAR_SCREEN = "\u001b[2J"

  val scrapeMenu = Menu(
    Seq(
      Command(
        "Stock",
        (x) => {
          print("Company: ")
          val company = readLine()
          print("Ticker symbol: ")
          val symbol = readLine()
          val responseString = AlphaVantage.StockScraper.scrape(Stock(company, symbol))
          Files.write(Paths.get(s"${DATA_DIRECTORY}${company}.csv"), responseString.getBytes())
          //Strip the first line (column headers) before parsing object
          var rows = responseString.split("\n")
          rows = Arrays.copyOfRange(rows, 1, rows.length)
          var recordsList = List[AlphaVantage.StockRecord]()
          rows.foreach((row) => {
            val columns = row.split(",")
            val record = AlphaVantage.StockRecord(columns(0), columns(1).toFloat, columns(2).toFloat, columns(3).toFloat, columns(4).toFloat, columns(5).toFloat)
            recordsList = recordsList :+ record
          })
          Files.write(Paths.get(s"${DATA_DIRECTORY}${company}.json"), write(recordsList).getBytes())
        }
        ),
        
        Command(
          "Cryptocurrency",
          (x) => {
            print("Currency: ")
          val currency = readLine()
          print("Ticker symbol: ")
          val symbol = readLine()
          val responseString = AlphaVantage.CryptocurrencyScraper.scrape(Cryptocurrency(currency, symbol))
          Files.write(Paths.get(s"${DATA_DIRECTORY}${currency}.csv"), responseString.getBytes())
          //Strip the first line (column headers) before parsing object
          var rows = responseString.split("\n")
          rows = Arrays.copyOfRange(rows, 1, rows.length)
          var recordsList = List[AlphaVantage.CryptocurrencyRecord]()
          rows.foreach((row) => {
            val columns = row.split(",")
            val record = AlphaVantage.CryptocurrencyRecord(columns(0), columns(1).toFloat, columns(2).toFloat, columns(3).toFloat, columns(4).toFloat, columns(5).toFloat, columns(6).toFloat, columns(7).toFloat, columns(8).toFloat, columns(9).toFloat, columns(10).toFloat)
            recordsList = recordsList :+ record
          })
          Files.write(Paths.get(s"${DATA_DIRECTORY}${currency}.json"), write(recordsList).getBytes())
        }
      ),

      new Back()
    ),
    "Which type of security?"
  )
  
  val analysisMenu = Menu(
    Seq(
      Command(
        "X",
        (x) => {
          println("TODO")
        }
      ),

      Command(
        "Y",
        (x) => {
          println("TODO")
        }
      ),

      Command(
        "Z",
        (x) => {
          println("TODO")
        }
      ),

      new Back()
    ),
    "Run which analysis?"
  )

  val mainMenu = Menu(
    Seq(
      Submenu("Scrape securities data from APIs", scrapeMenu),
      Command("Load entire results database", (x) => {
        println("TODO\nPress Enter to continue")
        readLine()
      }),
      Submenu("Perform data analyses", analysisMenu),
      Submenu("Example submenu", Menu(
        Seq(
          new Noop("This option does nothing"),
          new Back("Return to the previous menu")
        )
      )),
      Command("Quit", (x) => x.pop())
    ),
    "Main menu"
  )
  
  print(CLEAR_SCREEN)
  println(SPLASH_MESSAGE)

  var dataDirPath = Paths.get(DATA_DIRECTORY)
  var extraLine = false

  if(!Files.exists(dataDirPath)) {
    Files.createDirectory(dataDirPath)
    println(s"Initialized new data directory at '${dataDirPath}'.")
    extraLine = true
  }

  val dbFilePath = Paths.get(s"${DATA_DIRECTORY}${SECURITIES_DB_FILE}")

  if(!Files.exists(dbFilePath)) {
    Files.createFile(dbFilePath)
    Files.write(dbFilePath, "{}".getBytes())
    println(s"Initialized new database at '${dbFilePath}'")
    extraLine = true
  }

  if(extraLine) println()

  var menuSystem = new MenuSystem(mainMenu)
  var input = 0

  while(menuSystem.stack.length > 0) {
    try {
      menuSystem.render()
      input = readInt()
      print(CLEAR_SCREEN)
      menuSystem.select(input)
    } catch {
      case e: IndexOutOfBoundsException => {
        println("Error: That choice isn't on the menu\nPress Enter to try again")
        readLine()
      }

      case e: NumberFormatException => {
        println("Error: That isn't an integer\nPress Enter to try again")
        readLine()
      }

      case e: Throwable => {
        println(s"Error: ${e}\nPress Enter to try again")
        readLine()
      }
    } finally {
      print(CLEAR_SCREEN)
    }
  }
}