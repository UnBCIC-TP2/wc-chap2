package wc

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.GivenWhenThen
import org.scalatest.BeforeAndAfter


class ParserTest extends FlatSpec with Matchers with GivenWhenThen {
  behavior of "simple parser"

  it should "return [\"3\", \"4\", \"+\"] when we call parse(\"3 4 +\")" in {
    val res = Parser.parse("3 4 +")
    res should be (List("3", "4", "+"))
  }

}
