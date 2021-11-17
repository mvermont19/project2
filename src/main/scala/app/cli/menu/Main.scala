package app.cli.menu

import app._
import app.cli._

object Main extends Menu(
  Seq(
    Submenu("Scrape cryptocurrency data from APIs to local file system", Scrape),
    Command("(Re)Load results database from local file system to HDFS", _ => securitiesDf = load),
    Submenu("Perform data analyses", Analysis),
    Command("Quit", x => x.pop())
  ),
  "Main menu"
)