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

        println("Filter which coin?")
        val name = readLine

        var test = securitiesDf.get.as[SecuritiesDb].select($"securities")
        var test2 = test.select($"securities", explode($"securities")).toDF("securities", "element").select(
          $"securities",
          $"element.name",
          $"element.symbol",
          $"element.priceHistory",
          $"element.relevantArticles",
          $"element.relevantTweets"
        ).filter($"name" === s"$name")
        test2.printSchema
        test2.show
        readLine

        val test3 = test2.select($"securities", explode($"priceHistory")).toDF("securities", "priceHistory")
        test3.printSchema
        test3.show
        readLine

        val test5 = test3.select($"priceHistory").toDF("priceHistory")
        test5.printSchema
        test5.show
        readLine

        val test6 = test5.select(
          $"priceHistory.date",
          $"priceHistory.high",
          $"priceHistory.low"
        )
        test6.printSchema
        test6.show
        readLine

        val test4 = test6.select($"date", $"high", $"low").filter($"date" === java.time.LocalDate.now.toString)
        test4.printSchema
        test4.show
        readLine

        /*
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
        */
      }
      case None => println(s"No data loaded in Spark. Try the menu item above this one first.\n$PRESS_ENTER")
      readLine()
    }),
    Submenu("Perform data analyses", Analysis),
    Command("Quit", (x) => x.pop())
  ),
  "Main menu"
)