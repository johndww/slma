import scala.io.Source

/**
  *
  * @author john.wright
  * @since Mar-2017
  */
object Server {
  def main(args: Array[String]): Unit = {
    val url = "https://www.scala-lang.org/documentation/getting-started.html"

    val html = Source.fromURL(url)
    val lines = html.getLines()
//    val lines = Iterable(
//  "<TR class=\"d124m36\">",
//  "<TD class=\"d124m49\"><span class=\"label\"># Units:</SPAN></TD>",
//  "<TD class=\"d124m48\"><SPAN class=\"field\">2</span></TD>",
//  "<TD class=\"d124m49\"><span class=\"formula label field\">TOTAL:</span></TD>")


    var currentCategory: Category = NoCategory
    val categoryValues = collection.mutable.Map.empty[Category, String]

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
    println(categoryValues)
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
        case value => Some(CategoryValue(value))
      }
    }
    else {
      None
    }
  }
}

sealed trait Category

case object NumUnits extends Category
case object UtilitiesPaidByOwner extends Category
case object Taxes extends Category
case object Price extends Category
case class CategoryValue(value: String) extends Category
case object NoCategory extends Category
