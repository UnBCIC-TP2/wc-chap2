package wc

import scala.io.Source 
import scala.collection.mutable.Stack

import scala.collection.mutable.HashMap
import scala.collection.immutable.ListMap

class Interpreter {

  var stack = Stack[Any]()

  def run(cmds: List[String]) {
    cmds.foreach(c => runCommand(c))
  }

  def runCommand(cmd: String) {
    cmd match {
      case "*"             => times()
      case "+"             => add()
      case "read"          => read()
      case "words"         => words()
      case "count"         => count()
      case "frequentWords" => frequentWords()
      case "print"         => println(stack.pop())
      case _               => push(cmd)
    }
  }

  def push(cmd: String) { stack.push(cmd) }

  def pop(): Any = stack.pop()

  def clear() { stack.clear() }

  def frequentWords() {
    val   n = pop().asInstanceOf[String].toInt
    val map = pop().asInstanceOf[HashMap[String, Int]]
    
    stack.push(ListMap(map.toSeq.sortWith(_._2 > _._2):_*).take(n))  
  }

  def count() {
    val words = pop().asInstanceOf[List[String]]
    val res = new HashMap[String, Int]()

    words.foreach(w =>
      if(res.contains(w)) {
	val count = res(w) + 1
	res += (w -> count)
      }
      else {
	res += (w -> 1)
      }
    )
    stack.push(res) 
  }
  
  def words() {
    val lines = pop().asInstanceOf[List[String]]
    stack.push(lines.flatMap(s => s.split(" ")).map(s => s.replaceAll("[^A-Za-z0-9]", "")))
  }
  
  def read() {
    val path = pop().asInstanceOf[String]
    stack.push(Source.fromURL(getClass.getResource("/" + path)).getLines.toList)
  }
  
  def times() {
    val v1 = pop().asInstanceOf[String].toInt
    val v2 = pop().asInstanceOf[String].toInt

    push((v1 * v2).toString)
  }

  def add() {
    val v1 = pop().asInstanceOf[String].toInt
    val v2 = pop().asInstanceOf[String].toInt

    push((v1 + v2).toString)
  }
  
}
