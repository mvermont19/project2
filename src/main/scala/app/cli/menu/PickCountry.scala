package app.cli.menu

import app._
import app.cli._
import data.analysis.Analysis._

object PickCountry extends Menu(
    Seq(
        Command("Compare USD with Chinese Yen", _ => compareCountry(1)),
        Command("Compare USD with British Pound", _ => compareCountry(2)),
        Command("Compare USD with Euro", _ => compareCountry(3)),
        new Back()
    ),
    "Choose a currency to compare to the value of each crypto in USD"
)