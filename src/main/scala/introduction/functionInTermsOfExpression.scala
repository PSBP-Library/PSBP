package introduction

def functionInTermsOfExpression[Z, Y]: Z => Y = 
  val abstraction =
    (parameter: Z) => 
      expression(unboundIdentifier = parameter)
  abstraction    
