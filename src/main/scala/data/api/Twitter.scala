package data.api

import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.NoTypeHints

import java.io.InputStream
import java.io.IOException
import java.net.{URL, HttpURLConnection}
import scala.util.Try

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

import com.github.nscala_time.time.Imports._

object Twitter {
    implicit val formats = Serialization.formats(NoTypeHints)

    val apiKey = Option(System.getenv(TWITTER_API_KEY_ENV)) match {
        case Some(key) => key
        case None => TWITTER_API_DEFAULT_KEY
    }

    case class UserObject(id: String, name: String, username: String)
    case class IncludesObject(users: List[UserObject])
    case class MetaObject(oldest_id: String, newest_id: String, result_count: Int, next_token: Option[String])
    case class TweetObject(author_id: String, id: String, text: String)
    case class Response(data: List[TweetObject], includes: IncludesObject, meta: MetaObject)

    case class Tweet(datePublished: String, username: String, text: String)

    def scrapeTweetsByUserId(userId: String, startDate: String, endDate: String): List[Tweet] = {
        //create http connection and set it to get
        val httpClient = new DefaultHttpClient();
        val get = new HttpGet(s"https://api.twitter.com/2/users/$userId/tweets?start_time=$startDate&end_time=$endDate&expansions=author_id&user.fields=username,name")
        //set header and add authorization token
        get.setHeader("Content-Type", "application/json")
        get.setHeader("Authorization", s"Bearer $apiKey")
        val httpResponse = httpClient.execute(get)
        val entity = httpResponse.getEntity()
        var content = ""
        var result = List[Tweet]()
        //if we get connection, then grab content from api
        if (entity != null) {
            val inputStream = entity.getContent()
            content = scala.io.Source.fromInputStream(inputStream).getLines.mkString
            inputStream.close
        }
        httpClient.getConnectionManager().shutdown()

        val responseObject = read[Response](content)
        responseObject.data.foreach(x => {
            result = result :+ Tweet(DateTime.now.toString, responseObject.includes.users(0).name, x.text)
        })
        return result
    }
}