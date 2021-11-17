package app.cli.menu

import app._
import app.cli.menu._

object Analysis extends Menu(
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