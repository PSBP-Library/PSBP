package psbp.internal.implementation.computation.transformation.writing

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

// import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

private[psbp] given writingTransformedWriting[
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