package plp.internal.implementation.computation.transformation.reading

private[plp] type ReadingTransformed[
  R
  , C[+ _]
] = [Y] =>> R ?=> C[Y]  