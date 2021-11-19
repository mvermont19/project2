package data.analysis

import data.schema._
import misc._
import data.api._
import app._
import com.github.nscala_time.time.Imports._
import java.time.LocalDate._
import org.apache.spark.sql._ 
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._


import collection.mutable.Map._
import java.time.LocalDate

object Analysis {
    /** Find the highest price a given security ever traded at
    * @param security A stock symbol or cryptocurrency
    * @return The highest price the security ever traded at and the date that occurred
    */
    val obj = AllToDF
    val all = obj.crypto
    //all.loadCryptoData()

    def recentHighPrice(): Unit = {

        val df = all
        //df.show(1000000)
        val temp = df.select(df("CryptoCurrency"), df("timestamp"), df("USHigh"), df("ForeignCurrency"))
        .filter(df("ForeignCurrency") === "CNY")
        
        temp.select(df("CryptoCurrency"), df("timestamp"), df("USHigh"))
        .filter(df("timestamp") === LocalDate.now().toString() + " 00:00:00" ).show(false)

        println(PRESS_ENTER)
        readLine()

    }

    /** Find the lowest price a given currency ever traded at
     * @param security A stock symbol or cryptocurrency
     * @return The lowest price the security ever traded at and the date that occurred
    */
    def recentLowPrice(): Unit = {
        //val all = new AllToDF()
        val df = all
        val temp = df.select(df("CryptoCurrency"), df("timestamp"), df("USLow"), df("ForeignCurrency"))
        .filter(df("ForeignCurrency") === "CNY")
        
        temp.select(df("CryptoCurrency"), df("timestamp"), df("USLow"))
        .filter(df("timestamp") === LocalDate.now().toString() + " 00:00:00" ).show(false)

        println(PRESS_ENTER)
        readLine()
    }

    def specificDate(choice: Int, coin: String): Unit = {
        val dateForm = new DateFormatter()
        choice match {
            case 1 => {

                dateForm.askForDate(false)
                //val all = new AllToDF()
                val df = all
                val temp = df.select(df("CryptoCurrency"), df("timestamp"), df("USHigh"), df("USLow"), df("ForeignCurrency"))
                    .filter(df("ForeignCurrency") === "CNY")
                    
                temp.select(df("CryptoCurrency"), df("timestamp"), df("USHigh"), df("USLow"))
                    .filter(df("CryptoCurrency") === coin && df("timestamp") === dateForm.sd + " 00:00:00").show(false)

                println(PRESS_ENTER)
                readLine()
            }
            
            case 2 => {

                dateForm.askForWeek(false)

                //val all = new AllToDF()
                val df = all
                var counter = 0
                val temp = df.select(df("CryptoCurrency"), df("timestamp"), df("USHigh"), df("USLow"), df("ForeignCurrency"))
                    .filter(df("ForeignCurrency") === "CNY")
                    
                var start = dateForm.sd.substring(dateForm.sd.length()-2, dateForm.sd.length()).toInt
                while(counter < 7){
                    temp.select(df("timestamp"), df("CryptoCurrency"), df("USHigh"), df("USLow"))
                        .filter(df("CryptoCurrency") === coin && df("timestamp") === dateForm.sd + " 00:00:00").show()
                    counter += 1
                    start += 1
                    var tempDate = ""
                    if(start < 10){
                        tempDate = "0" + start.toString()
                    }
                    else{
                        tempDate = start.toString()
                    }
                    //println("start.toString " + start.toString())
                    dateForm.sd = dateForm.sd.substring(0, dateForm.sd.length()-2) + tempDate
                    //println(dateForm.sd)
                }
                
                println(PRESS_ENTER)
                readLine()
            }
            
            case 3 => {

                dateForm.askForMonth(false)
                
                val df = all
                var counter = 0
                val temp = df.select(df("CryptoCurrency"), df("timestamp"), df("USHigh"), df("USLow"), df("ForeignCurrency"))
                    .filter(df("ForeignCurrency") === "CNY")
                    
                var start = dateForm.sd.substring(dateForm.sd.length()-2, dateForm.sd.length()).toInt
                while(counter < 30){
                    temp.select(df("timestamp"), df("CryptoCurrency"), df("USHigh"), df("USLow"))
                        .filter(df("CryptoCurrency") === coin && df("timestamp") === dateForm.sd + " 00:00:00").show()
                    counter += 1
                    start += 1
                    var tempDate = ""
                    if(start < 10){
                        tempDate = "0" + start.toString()
                    }
                    else{
                        tempDate = start.toString()
                    }
                    //println("start.toString " + start.toString())
                    dateForm.sd = dateForm.sd.substring(0, dateForm.sd.length()-2) + tempDate
                    //println(dateForm.sd)
                }
                println(PRESS_ENTER)
                readLine()
            }

            case 4 => {

                dateForm.askForYear(false)
                val df = all
                
                var monthCounter = 0
                val temp = df.select(df("CryptoCurrency"), df("timestamp"), df("USHigh"), df("USLow"), df("ForeignCurrency"))
                    .filter(df("ForeignCurrency") === "CNY")
                    
                //var startDay = dateForm.sd.substring(dateForm.sd.length()-2, dateForm.sd.length()).toInt
                var startMonth = dateForm.sd.substring(dateForm.sd.length()-5, dateForm.sd.length()-3).toInt
                while(monthCounter < 12){
                    var dayCounter = 0
                    var startDay = dateForm.sd.substring(dateForm.sd.length()-2, dateForm.sd.length()).toInt
                   // println("startDay: " + startDay)
                   // println("startMonth: " + startMonth)
                    while(dayCounter < 31){
                        temp.select(df("timestamp"), df("CryptoCurrency"), df("USHigh"), df("USLow"))
                        .filter(df("CryptoCurrency") === coin && df("timestamp") === dateForm.sd + " 00:00:00").show()
                        dayCounter += 1
                        startDay += 1
                        var tempDate = ""
                        if(startDay < 10){
                            tempDate = "0" + startDay.toString()
                        }
                        else{
                            tempDate = startDay.toString()
                        }
                        //println("start.toString " + start.toString())
                        dateForm.sd = dateForm.sd.substring(0, dateForm.sd.length()-2) + tempDate
                        //println(dateForm.sd)
                    }
                    monthCounter += 1
                    startMonth += 1
                    var tempDate = ""
                    if(startMonth < 10){
                        tempDate = "0" + startMonth.toString()
                    }
                    else{
                        tempDate = startMonth.toString()
                    }
                    dateForm.sd = dateForm.sd.substring(0, dateForm.sd.length()-5) + 
                        tempDate + dateForm.sd.charAt(dateForm.sd.length()-3) + "01"
                    //println(dateForm.sd)
                }
                println(PRESS_ENTER)
                readLine()
            }
        }

    }
    
    def compareCountry(choice: Int): Unit = {
        choice match{
            case 1 => {
                val df = all
                val temp = df.select(df("CryptoCurrency"), df("ForeignCurrency"), df("timestamp"), df("USHigh"), df("USLow"), df("ForeignHigh"), df("ForeignLow"))
                .filter(df("ForeignCurrency") === "CNY" && df("timestamp") === LocalDate.now().toString() + " 00:00:00" ).show(false)
                

                println(PRESS_ENTER)
                readLine()
            }
            case 2 => {
                val df = all
                val temp = df.select(df("CryptoCurrency"), df("ForeignCurrency"), df("timestamp"), df("USHigh"), df("USLow"), df("ForeignHigh"), df("ForeignLow"))
                .filter(df("ForeignCurrency") === "GBP" && df("timestamp") === LocalDate.now().toString() + " 00:00:00" ).show(false)
                

                println(PRESS_ENTER)
                readLine()
            }
            case 3 => {
                val df = all
                val temp = df.select(df("CryptoCurrency"), df("ForeignCurrency"), df("timestamp"), df("USHigh"), df("USLow"), df("ForeignHigh"), df("ForeignLow"))
                .filter(df("ForeignCurrency") === "EUR" && df("timestamp") === LocalDate.now().toString() + " 00:00:00" ).show(false)
                

                println(PRESS_ENTER)
                readLine()
            }
        }
    }

    def findTweets(name: String, abbr: String): Unit = {
<<<<<<< HEAD

        val dateForm = new DateFormatter()
        dateForm.askForDate(false)
        val startDate = dateForm.startDate(dateForm.sd)
        val endDate = dateForm.endDate(dateForm.sd)
        obj.showTweets(name.toLowerCase(), startDate, endDate, dateForm.sd)

        println(PRESS_ENTER)
        readLine()
        
    }

    def averages(coin: String): Unit = {
        val df = all
        val temp = df.select(df("CryptoCurrency"), df("timestamp"), df("USHigh"), df("USLow"), df("ForeignCurrency"))
        .filter(df("ForeignCurrency") === "CNY" && df("CryptoCurrency") === coin)
        
        temp.groupBy(df("CryptoCurrency")).avg("USHigh", "USLow").show(false)

        println(PRESS_ENTER)
        readLine()
    }

    def findArticle(coin: String, abbr: String){
        val art = articles
        val spark = SparkSession.builder.getOrCreate()
        val rdd = spark.sparkContext.parallelize(art)
        val df = spark.createDataFrame(rdd)

        df.printSchema()
        df.show()

        println(PRESS_ENTER)
        readLine()
=======
        println("*********************************************")
        println("Where we get tweets " + name)
        println("*********************************************")
        askForDate()
        val startDate = startDate(date)
        val endDate = endDate(date)
        showTweets(name, startDate, endDate, date)
>>>>>>> a8dc1ccd7851e7e8200c2fe85278c3cc662d02b3
    }

}