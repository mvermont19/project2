package example

import misc._

object TwitterTest {
    def main(args: Array[String]) = {
        val crypto = scala.io.StdIn.readLine("what is the name of the crypto you would like to search? ")
        val date = scala.io.StdIn.readLine("what is the date you would like to search? ")
        val dateFormat = new DateFormatter()
        val startDate = dateFormat.startDate(date)
        val endDate = dateFormat.endDate(date)
        val twitterTester = new TwitterToDF()
        twitterTester.showTweets(crypto, startDate, endDate, date)
    }
}