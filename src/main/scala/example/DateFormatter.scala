package example


import java.io._
import scala.io._

import java.time.{Instant, LocalDate}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}
import scala.util.Try
import scala.util.Failure

class DateFormatter {

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
