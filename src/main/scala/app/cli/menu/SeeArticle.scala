package app.cli.menu

import app._
import app.cli._
import data.analysis._
import data.analysis.Analysis._

object SeeArticle extends Menu(
    Seq[MenuItem](),
    "Which cryptocurrency?"
    ) {
        items = Seq()

        CRYPTOCURRENCIES.foreach(x => {
        items = items :+ Command(
            s"${x._1} (${x._2})",
            _ => findArticle(x._1, x._2)
            )
        })

    items = items :+ new Back()
    }