package app.cli.menu

import app._
import app.cli._
import data.analysis.Analysis._
import app.cli.menu._
import java.awt.image.AreaAveragingScaleFilter


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
      "Average of each coin", Averages
    ),

    Submenu(
      "Compare Different Country Currency", PickCountry
    ),

    Submenu(
      "See related Tweets", SeeTweet
    ),

    Submenu(
      "See related Articles", SeeArticle
    ),
    
    new Back()
  ),
  "Run which analysis?"
)