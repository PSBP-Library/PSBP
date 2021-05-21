package plp.internal.implementation.computation.transformation.reading

import plp.external.specification.reading.Reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenReadingTransformedReading[
  R
  , D[+ _]: Computation
]: Reading[
  R
  , ProgramFromComputation[ReadingTransformed[R, D]]
] with

  private type C[+Y] = ReadingTransformed[R, D][Y]

  private type `=>C` = [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    `i?~>c` => `i?~>d`
  }

  override def read: Unit `=>C` R =
    given Unit = ()
      `i?~>d`.apply // (summon[R])