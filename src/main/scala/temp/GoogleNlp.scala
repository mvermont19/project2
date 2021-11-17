package data.api

import data.api._

object Google {
    val apiKey = Option(System.getenv(GOOGLE_API_KEY_ENV)) match {
        case Some(key) => key
        case None => GOOGLE_API_DEFAULT_KEY
    }

    //TODO
    case class Sentiment(text: String)
}