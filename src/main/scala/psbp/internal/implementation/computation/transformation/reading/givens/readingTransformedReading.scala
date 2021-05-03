package psbp.internal.implementation.computation.transformation.reading.givens

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedReading[
  R: Readable
  , C[+ _]: Computation
]: Reading[
  R
  , [Z, Y] =>> Z => ReadingTransformed[R, C][Y]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>T` = [Z, Y] =>> Z => T[Y]

  private val computation: Computation[F] = 
    summon[Computation[F]]
  import computation.{ 
    result => resultF
  }

  override def read: Unit `=>T` R =
    _ =>
      val r: R = summon[R]
      resultF(r)