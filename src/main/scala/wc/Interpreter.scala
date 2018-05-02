package wc

import scala.collection.mutable.Stack

class Interpreter {

  var stack = Stack[String]()

  def run(cmds: List[String]) {
    cmds.foreach(c => runCommand(c))
  }

  def runCommand(cmd: String) {
    cmd match {
      case "*" => times()
      case "+" => add() 
      case _   => push(cmd)   
    }
  }

  def push(cmd: String) { stack.push(cmd) }

  def pop(): String = stack.pop()

  def times() {
    val v1 = pop().toInt
    val v2 = pop().toInt

    push((v1 * v2).toString)
  }

  def add() {
    val v1 = pop().toInt
    val v2 = pop().toInt

    push((v1 + v2).toString)
  }
}
