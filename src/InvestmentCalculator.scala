/**
  * @author john.wright
  * @since Mar-2017
  */
class InvestmentCalculator extends RentalIncome {

  /**
    * Calculates the ROI on an investment listing
    *
    * @param lines parseable listing data
    * @return ROI
    */
  def calculate(lines: Iterator[String]): Double = {
    val categoryValues = parseListing(lines)

    val rentalIncome = calculateRentalIncome(categoryValues)

    //TODO return the calculated ROI value
    50.00
  }

  def parseListing(lines: Iterator[String]): Map[Category, String] = {
    val categoryValues = collection.mutable.Map.empty[Category, String]

    var currentCategory: Category = NoCategory

    for (line <- lines) {
      if (currentCategory == NoCategory) {
        currentCategory = parseCategory(line) match {
          case Some(category) => category
          case None => NoCategory
        }
      }
      else {
        parseCategory(line) match {
          case Some(CategoryValue(value)) => categoryValues.put(currentCategory, value)
          case _ =>
        }
        currentCategory = NoCategory
      }
    }
    categoryValues.toMap
  }

  def parseCategory(line: String): Option[Category] = {
    val pattern = "(?i)class=\"d124m[0-9]{2}\".*?><span.*?>(.*?)<\\/SPAN>".r
    val found = pattern.findAllIn(line)
    if (found.hasNext) {
      found.group(1) match {
        case "# Units:" => Some(NumUnits)
        case "List Price:" => Some(Price)
        case "Paid by Owner:" => Some(UtilitiesPaidByOwner)
        case "Annual Taxes:" => Some(Taxes)
        case "Total Rooms:" => Some(NumRooms)
        case "Area:" => Some(PropertyLocation)
        case value => Some(CategoryValue(value))
      }
    }
    else {
      None
    }
  }
}
