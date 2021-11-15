package app

import app._
import misc._
import scala.io.StdIn.readLine

object Cli extends App {
  val SPLASH_MESSAGE = s"Revature Project 2 - Cryptocurrency Analysis w/ Spark v${APP_VERSION}\n"
  val CLEAR_SCREEN = "\u001b[2J"

  val scrapeMenu = Menu(
    Seq(
      Command(
        "Stock",
        (x) => {
          println("TODO")
        }
      ),

      Command(
        "Cryptocurrency",
        (x) => {
          println("TODO")
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
      Submenu("Enter submenu", Menu(
        Seq(
          Command("Noop", (x) => {}),
          Command("Back", (x) => x.pop())
        ),
        "Submenu",
        "Prompt:"
      )),
      Submenu("Scrape securities info from APIs", scrapeMenu),
      Command("Load entire results database", (x) => {
        println("TODO\nPress Enter to continue")
        readLine()
      }),
      Submenu("Perform data analyses", analysisMenu),
      Command("Quit", (x) => x.pop())
    ),
    "Main menu"
  )
      
  var menuSystem = new MenuSystem(mainMenu)
  
  print(CLEAR_SCREEN)
  println(SPLASH_MESSAGE)

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