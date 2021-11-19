package data.analysis

object `package` {
  val CRYPTOCURRENCIES = Map(
    "Bitcoin" -> "BTC",
    "Ethereum" -> "ETH",
    "Solana" -> "SOL",
    "Loopring" -> "LRC",
    "Polkadot" -> "DOT",
    "Dogecoin" -> "DOGE",
    "TRON" -> "TRX",
    "Terra" -> "LUNA",
    "Algorand" -> "ALGO",
    "Neo" -> "NEO",
    "Litecoin" -> "LTC",
    "Cosmos" -> "ATOM",
    "Polygon" -> "MATIC",
    "Ripple" -> "XRP",
    "Boba" -> "BOBA"
  )

  val CURRENCIES = Map(
    "GBP" -> "British Pound Sterling",
    "EUR" -> "Euro",
    "CNY" -> "Chinese Yuan"
  )

  //This is the map that has all the crypto and the ids of the twitter pages to run through the requests in a for loop
  val CRYPTO_TO_TWITTER = Map(
    "ETH" -> List("2312333412", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "BTC" -> List("361289499", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "SOL" -> List("951329744804392960", "1387497871751196672", "3367334171", "1333467482", "241664456","928759224599040001"),
    "XLA" -> List("1051053836", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "LRC" -> List("9130922", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "DOT" -> List("1595615893", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "BOBA" -> List("831847934534746114", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "DOGE" -> List("2235729541", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "TRON" -> List("894231710065446912", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "LUNA" -> List("1022028994772910086", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "ALGO" -> List("927909832002277376", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "NEO" -> List("2592325530", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "LTC" -> List("385562752", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "ATOM" -> List("15223775", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001"),
    "MATIC" -> List("914738730740715521", "1387497871751196672", "3367334171", "1333467482", "241664456", "928759224599040001")
  )
}