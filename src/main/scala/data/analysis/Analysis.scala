package data.analysis

import data.analysis._
import misc._
import com.github.nscala_time.time.Imports._

object Analysis {
    /** Find the highest price a given security ever traded at
    * @param security A stock symbol or cryptocurrency
    * @return The highest price the security ever traded at and the date that occurred
    */
    def historicalHighPrice(security: Security): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find the lowest price a given currency ever traded at
     * @param security A stock symbol or cryptocurrency
     * @return The lowest price the security ever traded at and the date that occurred
    */
    def historicalLowPrice(security: Security): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }
    //}

    /** Find a security's most active trading day ever
     * @param security A stock symbol or cryptocurrency
     * @return The most units the security traded in any day and the date that occurred
    */
    def historicalHighVolume(security: Security): (Float, DateTime) = {
        //TODO
        return (0.0f, DateTime.now())
    }

    /** Find a security's least active trading day ever
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