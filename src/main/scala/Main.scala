import scala.io.StdIn

import wc.Parser._
import wc.Interpreter

object Main extends App {
  val interpreter = new Interpreter() 
  var continue = true
  
  while(continue) {     
    print("wc-interpreter > ")
    var program = StdIn.readLine()

    var cmds = parse(program)

    interpreter.clear()
    interpreter.run(cmds)

    if(program == "break") continue = false;
  }
  
}