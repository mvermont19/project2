package data.api

object Twitter {
    val apiKey = Option(System.getenv(TWITTER_API_KEY_ENV)) match {
        case Some(key) => key
        case None => TWITTER_API_DEFAULT_KEY
    }

    //TODO
    case class Tweet(username: String, text: String, verified: Boolean)
}