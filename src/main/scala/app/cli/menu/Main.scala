package app.cli.menu

import app._
import app.cli.menu._

object Main extends Menu(
  Seq(
    Submenu("Scrape cryptocurrency data from APIs to local file system", Scrape),
    Command("(Re)Load results database from local file system to HDFS", _ => securitiesDf = Some(load)),
    Command("Scratchpad", _ => securitiesDf match {
      case Some(x) => {
        import data.schema._
        import org.apache.spark.sql._
        import org.apache.spark.sql.functions._
        val spark = sparkSession.get
        import spark.implicits._

        var test = securitiesDf.get.as[SecuritiesDb].select($"securities")
        var test2 = test.select($"securities", explode($"securities")).toDF("securities", "element").select(
          $"securities",
          $"element.name",
          $"element.symbol",
          $"element.priceHistory",
          $"element.relevantArticles",
          $"element.relevantTweets"
        )
        test2.printSchema()
        test2.show()
        readLine()

        /*
        var df = securitiesDf.get.select(explode(col("securities")))
        df.printSchema()
        println()
        df.select(col("col.name"), col("col.symbol"), explode(col("col.priceHistory")), col("col.date"), col("col.open"), col("col.low")).show(false)
        println(PRESS_ENTER)
        readLine()

        df = df.select(explode(col("col.priceHistory")))
        df.printSchema()
        println()
        df.select(col("col.date"), col("col.open"), col("col.low"), col("col.high"), col("col.close"), col("col.volume"), col("col.marketCap")).show(false)
        println(PRESS_ENTER)
        readLine()
        */
      }
      case None => println(s"No data loaded in Spark. Try the menu item above this one first.\n$PRESS_ENTER")
      readLine()
    }),
    Submenu("Perform data analyses", Analysis),
    Command("Quit", x => x.pop())
  ),
  "Main menu"
)