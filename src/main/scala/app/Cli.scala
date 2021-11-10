package app

import scala.io.StdIn.readLine

object Cli extends App {
  val MAIN_MENU = "Main Menu\n 1: TODO\n 2: Quit"

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