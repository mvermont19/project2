package data.analysis

import data.schema._
import app._
import misc._
import com.github.nscala_time.time.Imports._

import org.apache.spark.sql.functions._

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
        df.select(col("col.high")).show(false)
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

        var df = securitiesDf.get.select(explode(col("securities")))
        df.select(col("col.name"), col("col.priceHistory.low")).show(false)
        println(PRESS_ENTER)
        readLine()
        //return (0.0f, DateTime.now())
    }

    def specificDate(choice: Int, coin: String) {
        val dateForm = new DateFormatter()

        choice match {
            case 1 => {
                println("*********************************************")
                println("Where we get specific day " + coin)
                println("*********************************************")

                
                dateForm.askForDate(false)
                //dateForm.sd = dateForm.startDate(dateForm.sd)
                //dateForm.ed = dateForm.startDate(dateForm.sd)

                var df = securitiesDf.get.select(explode(col("securities")))
                df.select(col("col.name"))
                    .filter(col("col.name") === coin )
                    .show(false)
                df = df.select(explode(col("col.priceHistory")))
                df.select(col("col.date"), col("col.high"), col("col.low"))
                    .filter(col("col.date") === dateForm.sd)
                    .show(false)
                println(PRESS_ENTER)
                readLine()
            }
            
            case 2 => {
                println("*********************************************")
                println("Where we get specific week "+ coin)
                println("*********************************************")

                dateForm.askForWeek(false)
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
    
    def compareCountry(choice: Int) {
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

    def findTweets(coin: String, abbr: String){
        println("*********************************************")
        println("Where we get tweets " + coin)
        println("*********************************************")
    }

    def findArticle(coin: String, abbr: String){

    }

}