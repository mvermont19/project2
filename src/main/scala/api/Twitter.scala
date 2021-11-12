package api

object Twitter {
    val TWITTER_API_KEY = Option(System.getenv(api.TWITTER_API_KEY_KEY)) match {
        case Some(key) => key
        case None => "TODO"
    }

    //TODO
    case class Tweet(username: String, text: String, verified: Boolean)
}