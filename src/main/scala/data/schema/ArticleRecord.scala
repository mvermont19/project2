package data.schema

import com.github.nscala_time.time.Imports._

case class ArticleRecord(timePublished: String, headline: String, headlineSentiment: Double, summary: String, sample: String)