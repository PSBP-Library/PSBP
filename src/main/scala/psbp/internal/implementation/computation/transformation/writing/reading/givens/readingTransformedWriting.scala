package psbp.internal.implementation.computation.transformation.writing.reading.givens

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedWriting[
  R: Readable
  , W: Writable
  , C[+ _]: [C[+ _]] =>> Writing[W, [Z, Y] =>> Z => C[Y]]
]: Writing[
  W
  , [Z, Y] =>> Z => ReadingTransformed[R, C][Y]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>F` = [Z, Y] =>> Z => F[Y]
  private type `=>T` = [Z, Y] =>> Z => T[Y]

  private val writing = summon[Writing[W, `=>F`]]
  import writing.{
    write => writeF
  }

  override def write: W `=>T` Unit =
    w =>
      writeF(w)