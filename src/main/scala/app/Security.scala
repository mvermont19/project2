package app

abstract class Security(val name: String, val symbol: String) {}

case class Stock(val name: String, val symbol: String) extends Security(name, symbol) {}

case class Cryptocurrency(val name: String, val symbol: String) extends Security(name, symbol) {}