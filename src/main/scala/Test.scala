def e[Z, Y](z: Z): Y = ???

def `z=>y`[Z, Y]: Z => Y = z => e(z)

def `z?=>y`[Z, Y]: Z ?=> Y = (z => e(z))(summon[Z])

object object1 {
  def `z?=>y`[Z, Y]: Z ?=> Y = ??? 

  def y[Y]: Y = `z?=>y`
}

object object2 {
  def y[Y]: Y = ???

  def `z?=>y`[Z, Y]: Z ?=> Y = y
}






