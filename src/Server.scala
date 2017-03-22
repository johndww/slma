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

    val calculator = new InvestmentCalculator()
    val roi = calculator.calculate(lines)

    println(s"ROI: $roi")
  }


}
