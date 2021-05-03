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
      case "top"           => println(stack.top)
      case _               => push(cmd)
    }
  }

  def push(cmd: String) { stack.push(cmd) }

  def pop(): Any = stack.pop()

  def clear() { stack.clear() }

  def frequentWords() {
    require(stack.size >= 2, "'frequentWords' requires two operands: the number of words to be displayed and " +
      "a map with the frequency of the words")
    val   n = pop().asInstanceOf[String].toInt
    val map = pop().asInstanceOf[HashMap[String, Int]]
    
    stack.push(ListMap(map.toSeq.sortWith(_._2 > _._2):_*).take(n))  
  }

  def count() {
    require(! stack.isEmpty, "'count' requires a list of words in the stack")

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
    require(!stack.isEmpty, "'words' requires a list of line in the top of the stack")
    val lines = pop().asInstanceOf[List[String]]
    stack.push(lines
      .flatMap(s => s.split(" "))                              // converts a list of string lists into a list of strings
      .map(s => s.replaceAll("[^A-Za-z0-9]", ""))
      .filter(s => s.size > 3 && !stopWords.contains(s))
    )
  }
  
  def read() {
    require(!stack.isEmpty, "'read' requires an absolute path to a file")
    val path = pop().asInstanceOf[String]
    stack.push(Source.fromFile(path).getLines.toList)
  }
  
  def times() {
    require(stack.size >= 2, "'times' requires two operands in the stack")
    val v1 = pop().asInstanceOf[String].toInt
    val v2 = pop().asInstanceOf[String].toInt

    push((v1 * v2).toString)
  }

  def add() {
    require(stack.size >= 2, "'add' requires two operands in the stack")
    val v1 = pop().asInstanceOf[String].toInt
    val v2 = pop().asInstanceOf[String].toInt

    push((v1 + v2).toString)
  }

  private val stopWords = Set("the", "about", "above", "after", "again", "against",
    "all", "and", "any", "because", "before", "below", "between", "but",
    "down", "during", "for", "from", "further", "here", "into", "more","once",
    "only", "other", "over", "same", "some", "such", "that", "then",
    "there", "these", "this", "those", "through", "under", "until", "very",
    "what", "when", "where", "which", "while", "who", "which",
    "with", "could", "were", "your", "have", "will", "been", "would",
    "they", "their", "should", "myself", "them", "upon", "might",
    "first", "eyes", "every", "you", "than", "thought", "whom", "ever",
    "most", "even","said", "shall", "towards", "found", "being",
    "time", "also", "him", "her", "still", "must", "many")
  
}
