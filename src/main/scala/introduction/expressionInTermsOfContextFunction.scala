package introduction

def expressionInTermsOfContextFunction[Z](value: Z): Nothing = 
  val expression =
    given hole: Z = value
    contextFunction
  expression 
