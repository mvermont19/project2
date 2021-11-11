package app

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.ml.stat.Correlation
import com.github.nscala_time.time.Imports._
import org.joda.time.Days

object Analysis {
    /** Find a highest price a given security traded at
    * @param security A stock symbol or cryptocurrency
    * @return The highest price the security ever traded at and the date that occurred
    */
    def historicalHighPrice(security: Security): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find a lowest price a given currency traded at
     * @param security A stock symbol or cryptocurrency
     * @return The lowest price the security ever traded at and the date that occurred
    */
    def historicalLowPrice(security: Security): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }
    //}

    /** Find a security's most active trading day
     * @param security A stock symbol or cryptocurrency
     * @return The most units the security traded in any day and the date that occurred
    */
    def historicalHighVolume(security: Security): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find a security's least active trading day
     * @param security A stock symbol or cryptocurrency
     * @return The least units the security traded in any day and the date that occurred
    */
    def historicalLowVolume(security: Security): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find the day a security received the most tweets
     * @param security A stock symbol or cryptocurrency
     * @return The most number of tweets about this security in a given day and the date that occurred
    */ 
    def historicalMostTweets(security: Security): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find the day a security received the least tweets
     * @param security A stock symbol or cryptocurrency
     * @return The least number of tweets about this security in a given day and the date that occurred
    */ 
    def historicalLeastTweets(security: Security): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }
}