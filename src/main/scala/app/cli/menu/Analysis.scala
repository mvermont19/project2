package app.cli.menu

import app._
import app.cli._
import data.analysis.Analysis._

object Analysis extends Menu(
  Seq(
    Command(
      "Highs of each coin", (x) => { recentHighPrice() }
    ),

    Command(
      "Lows of each coin", (x) => { recentLowPrice() }
    ),

    Submenu(
      "Specific Date", DateChoice
    ),

    Submenu(
      "Compare Different Country Currency", PickCountry
    ),

    Submenu(
      "See related Tweets", SeeTweet
    ),
    new Back()
  ),
  "Run which analysis?"
)