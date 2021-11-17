package app.cli.menu

import misc._
import app._
import app.cli._
import data.analysis._

object Scrape extends Menu(
  Seq[MenuItem](),
  "Which cryptocurrency?"
) {
  items = Seq()

  CRYPTOCURRENCIES.foreach(x => {
    items = items :+ Command(
      x._1,
      _ => scrape(Cryptocurrency(x._1, x._2))
    )
  })

  items = items :+ new Back()
}