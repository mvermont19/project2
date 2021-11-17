package data.schema

sealed trait TimePeriod
case object Day extends TimePeriod
case object Week extends TimePeriod
case object Month extends TimePeriod