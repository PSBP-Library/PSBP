package introduction

object valueAsContextFunction {
  def value[Y]: Y = ???

  def contextFunction[Z, Y]: Z ?=> Y = value
}