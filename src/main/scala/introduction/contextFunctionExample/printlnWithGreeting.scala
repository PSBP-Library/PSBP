package introduction.contextFunctionExample

def printlnWithGreeting[Z](value: Z): String ?=> Unit = 
  val helloValue: String = hello
  val goodbyeValue: String = goodbye
  println(s"$helloValue\n$value\n$goodbyeValue")