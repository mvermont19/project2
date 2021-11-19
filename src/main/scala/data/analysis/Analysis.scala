package data.analysis

import data.schema._
import app._
import misc._
import com.github.nscala_time.time.Imports._
import java.time.LocalDate._
import org.apache.spark.sql.functions._

import collection.mutable.Map._

object Analysis {
    /** Find the highest price a given security ever traded at
    * @param security A stock symbol or cryptocurrency
    * @return The highest price the security ever traded at and the date that occurred
    */
    def recentHighPrice(): Unit = {
        // println("*********************************************")
        // println("Where we get recent highs")
        // println("*********************************************")

        var df = securitiesDf.get.select(explode(col("securities")))
        df.select(col("col.name")).show(false)
        df = df.select(explode(col("col.priceHistory")))
        df.select(col("col.high"))
            .filter(col("col.date") === LocalDate.now().toString())
            .show(false)
        println(PRESS_ENTER)
        readLine()

        //return (0.0f, DateTime.now())
    }

    /** Find the lowest price a given currency ever traded at
     * @param security A stock symbol or cryptocurrency
     * @return The lowest price the security ever traded at and the date that occurred
    */
    def recentLowPrice(): Unit = {
        println("*********************************************")
        println("Where we get recent lows")
        println("*********************************************")
        //val date = LocalDate.now().toString()
        var df = securitiesDf.get.select(explode(col("securities")))
        df.select(col("col.name")).show(false)
        df = df.select(explode(col("col.priceHistory")))
        df.select(col("col.low"))
            .filter(col("col.date") === LocalDate.now().toString())
            .show(false)
        println(PRESS_ENTER)
        readLine()
        //return (0.0f, DateTime.now())
    }

    def specificDate(choice: Int, coin: String): Unit = {
        choice match {
            case 1 => {
                println("*********************************************")
                println("Where we get specific day " + coin)
                println("*********************************************")

                
                dateForm.askForDate(false)
                //dateForm.sd = dateForm.startDate(dateForm.sd)
                //dateForm.ed = dateForm.startDate(dateForm.sd)
                securitiesDf.get.show()
                // val names = securitiesDf.get.select(explode(col("securities.name")).as("names"), col("securities.priceHistory"))
                // names.show()
                // val price = names.select(col("names"), explode(col("priceHistory").as("prices")))
                // price.show()
                // val fin = price.select(col("names"), col("prices.date"), col("prices.high"))
                //           //  .filter(col("names") === coin && col("prices.date") === dateForm.sd)
                // fin.show()



                // val coinMap = Map[String, Array[Array[String]]]()
                // for(k <- coinMap.keySet){
                //     println(k + " : " + coinMap.get(k))
                // }

                //securitiesDf.get.select($"securities".getItem("priceHistory")(0)("high")).show(false)
                //df = df.withColumn("securities.name", explode(col("securities.name")))
                //df.groupBy("securities.name")
                // df = df.withColumn("securities.priceHistory", explode(col("securities.priceHistory")(0)))
                // df.show()
                // df.printSchema()
                // df = df.withColumn("securities.priceHistory.date", col("securities.priceHistory.date"))
                // df.show()
                // df.printSchema()
                //df.filter(col("securities.name") === coin)
                // df = df.select(col("col.securities.name"), col("col.securities.priceHistory"))
                //     .filter(col("col.securities.name") === coin )
                // //df = df.select(explode(col("col.priceHistory"))).filter(col("col.name") === coin )
                // df = df.select( explode(col("col.priceHistory")))
                // df.show(false)
                // df.select( col("col.date"), col("col.high"), col("col.low"))
                //     .filter(col("col.date") === dateForm.sd)
                //     .show(false)
                println(PRESS_ENTER)
                readLine()
            }
            
            case 2 => {
                println("*********************************************")
                println("Where we get specific week "+ coin)
                println("*********************************************")

                var start = dateForm.askForWeek(false)
                var df = securitiesDf.get.select(explode(col("securities")))
                df.select(col("col.name"))
                    .filter(col("col.name") === coin )
                    .show(false)
                df = df.select(explode(col("col.priceHistory")))
                var counter = 0
                while(counter < 7){
                    df.select(col("col.date"), col("col.high"), col("col.low"))
                    .filter(col("col.date") === dateForm.sd) //change
                    .show(false)
                    counter += 1
                    start += 1
                    dateForm.sd = dateForm.sd.substring(0, dateForm.sd.length()-3) + start.toString()
                }
                
                println(PRESS_ENTER)
                readLine()
            }
            
            case 3 => {
                println("*********************************************")
                println("Where we get specific month "+ coin)
                println("*********************************************")

                dateForm.askForMonth(false)
                var df = securitiesDf.get.select(explode(col("securities")))
                df.select(col("col.name"))
                    .filter(col("col.name") === coin )
                    .show(false)
                df = df.select(explode(col("col.priceHistory")))
                df.select(col("col.date"), col("col.high"), col("col.low"))
                    .filter(col("col.date") === dateForm.sd) //change
                    .show(false)
                println(PRESS_ENTER)
                readLine()
            }

            case 4 => {
                println("*********************************************")
                println("Where we get specfic year " + coin)
                println("*********************************************")

                dateForm.askForYear(false)
                var df = securitiesDf.get.select(explode(col("securities")))
                df.select(col("col.name"))
                    .filter(col("col.name") === coin )
                    .show(false)
                df = df.select(explode(col("col.priceHistory")))
                df.select(col("col.date"), col("col.high"), col("col.low"))
                    .filter(col("col.date") === dateForm.sd) // change
                    .show(false)
                println(PRESS_ENTER)
                readLine()
            }
        }

    }
    
    def compareCountry(choice: Int): Unit = {
        choice match{
            case 1 => {
        println("*********************************************")
        println("Where we compare to chinese ")
        println("*********************************************")
            }
            case 2 => {
        println("*********************************************")
        println("Where we compare to pound")
        println("*********************************************")
            }
            case 3 => {
        println("*********************************************")
        println("Where we compare to euro")
        println("*********************************************")
            }
        }
    }

    def findTweets(name: String, abbr: String): Unit = {
        println("*********************************************")
        println("Where we get tweets " + coin)
        println("*********************************************")
        askForDate()
        val startDate = startDate(date)
        val endDate = endDate(date)
        showTweets(name, startDate, endDate, date)
    }

    def findArticle(coin: String, abbr: String){

    }

}