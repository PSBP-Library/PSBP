package introduction.contextFunctionExample

def hello: String ?=> String = 
  s"Hello, ${summon[String]}"

def goodbye: String ?=> String = 
  s"Goodbye, ${summon[String]}"