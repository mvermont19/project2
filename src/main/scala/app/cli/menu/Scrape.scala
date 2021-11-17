package app.cli.menu

import data.schema._
import app._
import app.cli.menu._
import data.analysis._

object Scrape extends Menu(
  Seq[MenuItem](),
  "Which cryptocurrency?"
) {
  items = Seq()

  CRYPTOCURRENCIES.foreach(x => {
    items = items :+ Command(
      s"${x._1} (${x._2})",
      _ => scrape(Cryptocurrency(x._1, x._2))
    )
  })

  items = items :+ new Back()
}