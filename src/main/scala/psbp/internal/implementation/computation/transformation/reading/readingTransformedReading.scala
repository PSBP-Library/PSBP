package psbp.internal.implementation.computation.transformation.reading

import psbp.external.specification.reading.Reading

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

// import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedReading[
  R
  , C[+ _]: Computation
]: Reading[
  R
  , ProgramFromComputation[ReadingTransformed[R, C]]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
  }

  override def read: Unit `=>T` R =
    _ =>
      resultF(summon[R])