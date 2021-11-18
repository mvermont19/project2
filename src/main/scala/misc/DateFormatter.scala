package misc

import java.io._
import scala.io._

import java.time.{Instant, LocalDate}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import scala.util.Try
import scala.util.Failure

class DateFormatter {

  var sd = ""
  var ed = ""

  def askForDate(internalCall: Boolean) {
    print("What day would you like to look at (Numbered 01-30): ")
    val input = StdIn.readLine()
    sd = s"${askForYear(internalCall)}-${askForMonth(!internalCall)}-$input"
  }
  
  def askForWeek(internalCall: Boolean): Int = {
    println("What week would you like to look at (Numbered 01-23)")
    print("Start of week (end of week with be 7 days after): ")
    val input = StdIn.readLine()
    var end = input.toInt + 7
    val input2 = end.toString()
    val year = askForYear(internalCall)
    val month = askForMonth(!internalCall)
    sd = s"$year-$month-$input"
    ed = s"$year-$month-$input2"

    return input.toInt
  }

  def askForMonth(internalCall: Boolean): String = {
    print("What month would you like to look at (Numbered 01-12): ")
    val input = StdIn.readLine()
    if(!internalCall){
      sd = s"${askForYear(internalCall)}-$input-01"
      ed = s"${askForYear(!internalCall)}-$input-30"
    }
    return input
  }

  def askForYear(internalCall: Boolean): String = {
    var input = ""
    if(!internalCall){
      print("What year would you like to look at (Limit 2019-2021): ")
      input = StdIn.readLine()
      sd = s"$input-01-01"
      ed = s"$input-12-31"
    }
    return input
  }

  def startDate(input: String): String = {
    
    var startDate = ""
    val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
      LocalDate.parse(input, formatter2)
      startDate = input.concat("T00:00:00Z")
  }
    catch{
      case ex: Throwable =>
    }

    //var startDate = input.concat("T00:00:00Z")

    startDate 

  }

  def endDate(input:String): String = {

    var endDate = ""
    val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
      LocalDate.parse(input, formatter2)
      endDate = input.concat("T23:59:59Z")
    }
    catch{
      case ex: Throwable =>
    }

    endDate
  }
}
