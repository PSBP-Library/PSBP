package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.writing.{
  Writable
  , Writing
}

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenWritingTransformedWriting[
  W: Writable
  , C[+ _]: Computation
]: Writing[
  W
  , ProgramFromComputation[WritingTransformed[W, C]]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = WritingTransformed[W, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
  }

  override def write: W `=>T` Unit =
    w =>
      resultF((w, ()))