package introduction

def contextFunctionInTermsOfExpression[Z]: Z ?=> Nothing = 
  val expressionContext =
    val hole = summon[Z]
    expression(hole)
  expressionContext
