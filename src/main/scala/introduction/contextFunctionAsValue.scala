package introduction

object contextFunctionAsValue {
  def contextFunction[Z, Y]: Z ?=> Y = ??? 

  def value[Y]: Y = contextFunction
}
