package app

object Twitter {
    val apiKey = System.getenv(app.TWITTER_API_KEY_KEY)

    //TODO
    case class Tweet(username: String, text: String, verified: Boolean)
}