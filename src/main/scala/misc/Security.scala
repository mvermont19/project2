package misc

abstract class Security(val name: String, val symbol: String) {}

case class Stock(override val name: String, override val symbol: String) extends Security(name, symbol) {}

case class Cryptocurrency(override val name: String, override val symbol: String) extends Security(name, symbol) {}