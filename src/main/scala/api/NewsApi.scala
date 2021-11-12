package api

object NewsApi {
    val NEWS_API_KEY = Option(System.getenv(api.NEWS_API_KEY_KEY)) match {
        case Some(key) => key
        case None => "API_KEY"
    }

    //TODO
    case class Article(title: String, description: String)
}