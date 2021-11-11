package example

import java.io.InputStream
import java.io.IOException
import java.net.{URL, HttpURLConnection}
import scala.util.Try
import java.io._
import scala.io._

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient

object GetUrlContent extends App {
  // simpleApi()
  // var data = getRestContent(
  //   "https://reqres.in/api/users"
  // )
  // println(data)
class HdfsDemo {
  
  val path = "hdfs://sandbox-hdp.hortonworks.com:8020/user/maria_dev/"


  def createFile(json: String): Unit = {
    val hdfsFile = new APIConnect()
    val filename = path + "ApiData.txt"
    println(s"Creating file $filename ...")
    
    val conf = new Configuration()
    val fs = FileSystem.get(conf)
    
    // Check if file exists. If yes, delete it.
    println("Checking if it already exists...")
    val filepath = new Path( filename)
    val isExisting = fs.exists(filepath)
    if(isExisting) {
      println("Yes it does exist. Deleting it...")
      fs.delete(filepath, false)
    }

    val output = fs.create(new Path(filename))
    
    val writer = new PrintWriter(output)
    writer.write(json)
    writer.close()
    
    println(s"Done creating file $filename ...")
  }
}
  //data = getRestContent("https://gorest.co.in/public/v1/posts")
  println("Getting single tweet with id 1456337876564668418")
  var data2 = twitterApi("https://api.twitter.com/2/tweets/search/recent?query=cryptocurrency&start_time=2021-11-09T00:00:00Z&end_time=2021-11-09T11:59:59Z&tweet.fields=attachments,created_at,entities&expansions=attachments.media_keys&media.fields=duration_ms,height,media_key,preview_image_url,public_metrics,type,url,width")
  println(data2)
  
  /*println("\nGetting recent search...")
  data2 = twitterApi("https://api.twitter.com/2/tweets/search/recent?query=from:TwitterDev")
  println(data2)*/


  /*def simpleApi(): Unit = {
    val url = "http://api.hostip.info/get_json.php?ip=12.215.42.19"
    val result = scala.io.Source.fromURL(url).mkString
    println(result)
  }*/

  /** Returns the text content from a REST URL. Returns a blank String if there
    * is a problem. (Probably should use Option/Some/None; I didn't know about
    * it back then.)
    */
  /*def getRestContent(url: String): String = {
    val httpClient = new DefaultHttpClient()
    val httpResponse = httpClient.execute(new HttpGet(url))
    val entity = httpResponse.getEntity()
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent()
      content = scala.io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager().shutdown()
    return content
  }*/

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

//     JSONObject json = new JSONObject();
// // json.put ...
// // Send it as request body in the post request

//     StringEntity params = new StringEntity(json.toString());
//     post.setEntity(params);

//     HttpResponse response = httpclient.execute(post);
//     httpclient.getConnectionManager().shutdown();
  }
}