import scala.io.StdIn

import wc.Parser._
import wc.Interpreter

object Main extends App {
  val interpreter = new Interpreter() 
  var continue = true
  
  while(continue) {     
    try {
      print("wc-interpreter > ")
      val program = StdIn.readLine()
      val cmds = parse(program)

      interpreter.run(cmds)

      if (program == ":quit") continue = false;
    }
    catch {
      case e: Throwable => println(e)
    }
  }
  
}