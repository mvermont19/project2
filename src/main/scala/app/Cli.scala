package app

import app._
import scala.io.StdIn.readLine

object Cli extends App {
  var state = 0
  var menu = MAIN_MENU

  print("\u001b[2J")

  do {
    print(s"${menu + "\n\nOption: "}")

    state match {
      case 0 => {
        readLine() match {
          case "2" => state = -1
          case _ => {}
        }
      }
    }

    print("\u001b[2J")
  } while(state != -1)
}