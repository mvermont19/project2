package data.analysis

import data.schema._
import data.misc._
import data.api._
import app._
import com.github.nscala_time.time.Imports._

object Analysis {
    /** Find the highest price a given security ever traded at
    * @param security A stock symbol or cryptocurrency
    * @return The highest price the security ever traded at and the date that occurred
    */
    def recentHighPrice(): Unit = {
        println("*********************************************")
        println("Where we get recent highs")
        println("*********************************************")
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
        //return (0.0f, DateTime.now())
    }

    def specificDate(choice: Int, coin: String): Unit = {
        choice match {
            case 1 => {
        println("*********************************************")
        println("Where we get specific day " + coin)
        println("*********************************************")
            }
            
            case 2 => {
        println("*********************************************")
        println("Where we get specific week "+ coin)
        println("*********************************************")
            }
            
            case 3 => {
        println("*********************************************")
        println("Where we get specific month "+ coin)
        println("*********************************************")
            }

            case 4 => {
        println("*********************************************")
        println("Where we get specfic year " + coin)
        println("*********************************************")
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
        println("Where we get tweets " + name)
        println("*********************************************")
        val date = askForDate()
        val startDate = startDate(date)
        val endDate = endDate(date)
        showTweets(name, startDate, endDate, sd)
    }

}