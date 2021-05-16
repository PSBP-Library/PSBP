package psbp.internal.implementation.computation.transformation.writing.reading

import psbp.external.specification.reading.Reading

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.internal.specification.computation.Computation

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedWriting[
  R
  , W: Writable
  , C[+ _]: [C[+ _]] =>> Writing[W, ProgramFromComputation[C]]
]: Writing[
  W
  , ProgramFromComputation[ReadingTransformed[R, C]]
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