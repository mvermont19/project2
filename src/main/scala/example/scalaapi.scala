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

  /*//bitcoin
  var data2 = twitterApi("https://api.twitter.com/2/users/361289499/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name")
  println(data2)
  println("\nGetting recent search...")
 //ethereum
 https://api.twitter.com/2/users/2312333412/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
 //solana
 https://api.twitter.com/2/users/951329744804392960/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//ripple
https://api.twitter.com/2/users/1051053836/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name 
//loopring
https://api.twitter.com/2/users/9130922/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//polkadot
https://api.twitter.com/2/users/1595615893/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//bobanetwork
https://api.twitter.com/2/users/831847934534746114/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//dogecoin
https://api.twitter.com/2/users/2235729541/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//tron
https://api.twitter.com/2/users/894231710065446912/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//terra
https://api.twitter.com/2/users/1022028994772910086/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//algorand
https://api.twitter.com/2/users/927909832002277376/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//neo
https://api.twitter.com/2/users/2592325530/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//litecoin
https://api.twitter.com/2/users/385562752/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//cosmos
https://api.twitter.com/2/users/15223775/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//polygon
https://api.twitter.com/2/users/914738730740715521/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//watcher
https://api.twitter.com/2/users/1387497871751196672/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//bitcoin news
https://api.twitter.com/2/users/3367334171/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//coindesk
https://api.twitter.com/2/users/1333467482/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//cryptocurrency news
https://api.twitter.com/2/users/241664456/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
//bloombergcrypto
https://api.twitter.com/2/users/928759224599040001/tweets?start_time=2021-11-12T00:00:00Z&end_time=2021-11-12T23:59:59Z&user.fields=created_at,name
  */def twitterApi(url: String): String = {
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