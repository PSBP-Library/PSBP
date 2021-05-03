package psbp.internal.implementation.computation.transformation.reading

private[psbp] type ReadingTransformed[
  R
  , C[+ _]
] = [Y] =>> R ?=> C[Y]  