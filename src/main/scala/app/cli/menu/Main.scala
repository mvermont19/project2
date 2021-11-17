package app.cli.menu

import app._
import app.cli.menu._

object Main extends Menu(
  Seq(
    Submenu("Scrape cryptocurrency data from APIs to local file system", Scrape),
    Command("(Re)Load results database from local file system to HDFS", _ => securitiesDf = Some(load)),
    Command("Explosion experiment", _ => securitiesDf match {
      case Some(x) => {
        import org.apache.spark.sql.functions._
        var df = securitiesDf.get.select(explode(col("securities")))
        df.printSchema()
        println()
        df.select(col("col.name"), col("col.symbol")).show(false)
        println(PRESS_ENTER)
        readLine()

        df = df.select(explode(col("col.priceHistory")))
        df.printSchema()
        println()
        df.select(col("col.date"), col("col.open"), col("col.low"), col("col.high"), col("col.close"), col("col.volume"), col("col.marketCap")).show(false)
        println(PRESS_ENTER)
        readLine()
      }
      case None => println(s"No data loaded in Spark. Try the menu item above this one first.\n$PRESS_ENTER")
      readLine()
    }),
    Submenu("Perform data analyses", Analysis),
    Command("Quit", x => x.pop())
  ),
  "Main menu"
)