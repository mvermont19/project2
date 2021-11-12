package misc

sealed trait TimePeriod
case object Day extends TimePeriod
case object Week extends TimePeriod
case object Month extends TimePeriod