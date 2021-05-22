package plp.external.implementation

private[plp] def toFunction[Z, Y](`z?=>y`: Z ?=> Y): Z => Y =
  z =>
    given Z = z
    `z?=>y`

// extension [Z, Y] (`z?=>y`: Z ?=> Y) 
//   def asFunction: Z => Y =
//     toFunction(`z?=>y`)