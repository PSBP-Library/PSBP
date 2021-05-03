package psbp.internal.implementation.computation.transformation.writing.givens

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

private[psbp] given writingTransformedWriting[
  W: Writable
  , C[+ _]: Computation
]: Writing[
  W
  , [Z, Y] =>> Z => WritingTransformed[W, C][Y]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = WritingTransformed[W, C][Y]

  private type `=>T` = [Z, Y] =>> Z => T[Y]

  private val computation: Computation[F] = 
    summon[Computation[F]]
  import computation.{ 
    result => resultF
  }

  override def write: W `=>T` Unit =
    w =>
      resultF((w, ()))