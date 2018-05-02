package wc

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import org.scalatest.GivenWhenThen
import org.scalatest.BeforeAndAfter


class TestInterpreter extends FlatSpec with Matchers with GivenWhenThen {
  behavior of "a stack based interpreter"

  it should "return \"8\" when we call run [3, 4, +]" in {
    val interpreter = new Interpreter() 
    val p1  = Parser.parse("3 4 +")

    interpreter.run(p1)
    interpreter.pop() should be ("7") 
  }


  it should "return \"12\" when we call run [3, 4, *]" in {
    val interpreter = new Interpreter() 
    val p1  = Parser.parse("3 4 *")

    interpreter.run(p1)
    interpreter.pop() should be ("12") 
  }

}
