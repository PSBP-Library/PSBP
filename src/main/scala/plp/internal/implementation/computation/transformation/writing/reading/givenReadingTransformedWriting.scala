package plp.internal.implementation.computation.transformation.writing.reading

import plp.external.specification.reading.Reading

import plp.external.specification.writing.{
  Writable
  , Writing
}

import plp.internal.specification.computation.Computation

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[plp] given givenReadingTransformedWriting[
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