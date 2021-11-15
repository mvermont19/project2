package app

import app._
import misc._
import data.schema._
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
          scrape(Stock(company, symbol))
        }
        ),
        
        Command(
          "Cryptocurrency",
          (x) => {
            print("Currency: ")
            val currency = readLine()
            print("Ticker symbol: ")
            val symbol = readLine()
            scrape(Cryptocurrency(currency, symbol))
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
        db = read[SecuritiesDb](Files.readAllBytes(Paths.get(s"${DATA_DIRECTORY}${SECURITIES_DB_FILE}")).toString)
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