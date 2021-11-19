package data.analysis


import java.io._
import scala.io._

import java.time.{Instant, LocalDate}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import scala.util.Try
import scala.util.Failure

object DateFormatter {

  //takes the date input and tries to parse it
  def startDate(input: String): String = {
    var startDate = ""
    val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
      //if successful, it will concatenate the ISO date format on to the format for twitter query
      LocalDate.parse(input, formatter2)
      startDate = input.concat("T00:00:00Z")
    }
    catch{
      case ex: Throwable =>
    }
    //return this so we can pass the variable to the twitter query
    startDate 

  }
//does the same exact thing but adds on the end of the search term
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
