package app

object AlphaVantage {
    case class StockRecord(date: String, open: Float, high: Float, low: Float, close: Float, volume: Float)

    //DUNNO: valid date formats?
    case class CryptoRecord(date: String, openCny: Float, highCny: Float, lowCny: Float, closeCny: Float, openUsd: Float, highUsd: Float, lowUsd: Float, closeUsd: Float, volume: Float, marketCap: Float)
}