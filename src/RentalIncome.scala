/**
  * @author john.wright
  * @since Mar-2017
  */
trait RentalIncome {

  def calculateRentalIncome(categoryValues: Map[Category, String]): Double = {

    val numUnits = categoryValues.get(NumUnits) match {
      case Some(unitCount) => unitCount.toInt
      case None => throw new IllegalArgumentException("unable to parse the number of units")
    }

    val location = categoryValues.get(PropertyLocation) match {
      case Some("401-Parma") => Parma
      case Some("101-Lakewood") => Lakewood
    }

    //TODO parse the actual unit info
    val roomsPerUnit = 2

    location.getPricing().get(roomsPerUnit) match {
      case Some(price) => numUnits * price
      case None => throw new IllegalArgumentException(s"unable to find pricing for $roomsPerUnit in $location")
    }
  }
}

sealed trait Location {
  /**
    * @return map of bed room count -> rental price
    */
  def getPricing(): Map[Int, Double]
}
case object Parma extends Location {
  override def getPricing(): Map[Int, Double] = {
    Map(1->600, 2->750, 3->850)
  }
}
case object Lakewood extends Location {
  override def getPricing(): Map[Int, Double] = {
    Map(1->500, 2->650, 3->800)
  }
}