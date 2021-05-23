package plp.external.implementation

private[plp] def toFunction[Z, Y](`z?=>y`: Z ?=> Y): Z => Y =
  z =>
    given Z = z
    `z?=>y`

private[plp] def fromFunction[Z, Y](`z=>y`: Z => Y): Z ?=> Y =
  `z=>y`(summon[Z])

private[plp] def bind[Z, Y]: Z ?=> (Z ?=> Y) => Y =
 identity