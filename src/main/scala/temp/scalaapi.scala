package example

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

class twitterAPI {

  def twitterApi(url: String): String = {
    val httpClient = new DefaultHttpClient();
    //val  post = new HttpPost(url);
    val get = new HttpGet(url)
    get.setHeader("Content-Type", "application/json")
    get.setHeader("Authorization", "Bearer AAAAAAAAAAAAAAAAAAAAAN6sVgEAAAAAMMjMMWrwgGyv7YQOWN%2FSAsO5SGM%3Dg8MG9Jq93Rlllaok6eht7HvRCruN4Vpzp4NaVsZaaHHWSTzKI8")
    val httpResponse = httpClient.execute(get)
    val entity = httpResponse.getEntity()
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent()
      content = scala.io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager().shutdown()
    return content
  }
}