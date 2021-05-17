package plp.internal.implementation.computation.transformation.reading

import plp.external.specification.reading.Reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenReadingTransformedReading[
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