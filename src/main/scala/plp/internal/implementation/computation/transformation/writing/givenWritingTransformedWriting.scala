package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.writing.{
  Writable
  , Writing
}

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenWritingTransformedWriting[
  W: Writable
  , D[+ _]: Computation
]: Writing[
  W
  , ProgramFromComputation[WritingTransformed[W, D]]
] with

  private type C[+Y] = WritingTransformed[W, D][Y]

  private type `?=>C` = [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    result => resultD
  }

  override def write: W `?=>C` Unit =
    given (W, Unit) = (summon[W], ())
    resultD