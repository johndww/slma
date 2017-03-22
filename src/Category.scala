/**
  * @author john.wright
  * @since Mar-2017
  */
sealed trait Category

case object NumUnits extends Category
case object UtilitiesPaidByOwner extends Category
case object Taxes extends Category
case object Price extends Category
case object NumRooms extends Category
case object PropertyLocation extends Category
case class CategoryValue(value: String) extends Category
case object NoCategory extends Category
