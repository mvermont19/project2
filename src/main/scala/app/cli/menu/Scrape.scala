package app.cli.menu

import data.schema._
import data.api._
import app._
import app.cli.menu._
import data.analysis._

object Scrape extends Menu(
  Seq[MenuItem](),
  "Which cryptocurrency?"
) {
  CRYPTOCURRENCIES.foreach(x => {
    var currencies = List[MenuItem]()
    CURRENCIES.foreach(y => {
      currencies = currencies :+ Command(s"${y._1}", ctx => {
        scrape(Cryptocurrency(x._1, x._2), y._1)
        ctx.pop()
      })
    })

    items = items :+ Submenu(s"${x._1} (${x._2})", Menu(currencies, "Compare to which currency?"))
  })

  items = items :+ new Back()
}