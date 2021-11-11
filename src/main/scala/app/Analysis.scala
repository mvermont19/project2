package app

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.ml.stat.Correlation
import com.github.nscala_time.time.Imports._
import org.joda.time.Days

object Analysis {
    /** Find a highest price a given currency traded at
    * @param symbol A security's symbol
    * @return The highest price the security ever traded at
    */
    def historicalHighPrice(symbol: String): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find a lowest price a given currency traded at
     * @param symbol A security's symbol
     * @return The lowest price the security ever traded at
    */
    def historicalLowPrice(symbol: String): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }
    //}

    /** Find a security's most active trading day
     * @param symbol A security's symbol
     * @return The most units the security traded in any day
    */
    def historicalHighVolume(symbol: String): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find a security's least active trading day
     * @param symbol A security's symbol
     * @return The least units the security traded in any day
    */
    def historicalLowVolume(symbol: String): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find the day a company received the most tweets
    */ 
    def historicalMostTweets(symbol: String): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find the day a company received the least tweets
    */ 
    def historicalLeastTweets(symbol: String): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }
}