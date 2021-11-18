package data.schema

import com.github.nscala_time.time.Imports._

case class TweetRecord(timePublished: String, username: String, text: String)