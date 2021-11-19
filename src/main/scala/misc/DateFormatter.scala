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

  def askForDate(): Unit = {
    print("What day would you like to look at (Numbered 01-30): ")
    val input = StdIn.readLine()
    sd = s"$askForYear-$askForMonth-$input"
    sd = startDate(sd)
    ed = endDate(ed)
  }
  
  def askForWeek(): Unit = {
    println("What week would you like to look at (Numbered 01-30)")
    print("Start of week: ")
    val input = StdIn.readLine()
    print("End of week: ")
    val input2 = StdIn.readLine()
    sd = s"$askForYear-$askForMonth-$input"
    ed = s"$askForYear-$askForMonth-$input2"
  }

  def askForMonth(): String = {
    print("What month would you like to look at (Numbered 01-12): ")
    val input = StdIn.readLine()
    return input
  }

  def askForYear(): String = {
    print("What year would you like to look at (Limit 2019-2021): ")
    val input = StdIn.readLine()
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
