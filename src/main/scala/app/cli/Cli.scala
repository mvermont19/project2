package app.cli

import app._
import app.cli.menu._
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

  val SPLASH_MESSAGE = s"Revature Project 2 - Cryptocurrency Analysis w/ Spark v$APP_VERSION\n"
  val CLEAR_SCREEN = "\u001b[2J"
  
  print(CLEAR_SCREEN)
  println(SPLASH_MESSAGE)

  var dataDirPath = Paths.get(DATA_DIRECTORY)
  var extraLine = false

  if(!Files.exists(dataDirPath)) {
    Files.createDirectory(dataDirPath)
    println(s"Initialized new data directory at '$dataDirPath'.")
    extraLine = true
  }

  val dbFilePath = Paths.get(s"$DATA_DIRECTORY$SECURITIES_DB_FILE")

  if(!Files.exists(dbFilePath)) {
    Files.createFile(dbFilePath)
    Files.write(dbFilePath, "{}".getBytes())
    println(s"Initialized new database at '${dbFilePath}'")
    extraLine = true
  } else {
    securitiesDb = loadSecuritiesDb()
  }

  if(extraLine) {
    println(s"\n$PRESS_ENTER")
    readLine()
  }

  var menuSystem = new MenuSystem(app.cli.menu.Main)
  var input = 0

  while(menuSystem.stack.length > 0) {
    try {
      menuSystem.render()
      input = readInt()
      print(CLEAR_SCREEN)
      menuSystem.select(input)
    } catch {
      case e: IndexOutOfBoundsException => {
        println(s"Error: That choice isn't on the menu\n$PRESS_ENTER")
        readLine()
      }

      case e: NumberFormatException => {
        println(s"Error: That isn't an integer\n$PRESS_ENTER")
        readLine()
      }

      case e: Throwable => {
        println(s"Error: $e\n$PRESS_ENTER")
        readLine()
      }
    } finally {
      print(CLEAR_SCREEN)
    }
  }
}