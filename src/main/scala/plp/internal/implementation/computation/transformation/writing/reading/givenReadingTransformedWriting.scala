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
  , D[+ _]: [D[+ _]] =>> Writing[W, ProgramFromComputation[D]]
]: Writing[
  W
  , ProgramFromComputation[ReadingTransformed[R, D]]
] with

  private type C[+Y] = ReadingTransformed[R, D][Y]

  private type `=>D` = [Z, Y] =>> Z => D[Y]
  private type `=>C` = [Z, Y] =>> Z => C[Y]

  private val writing = summon[Writing[W, `=>D`]]
  import writing.{
    write => writeF
  }

  override def write: W `=>C` Unit =
    w =>
      writeF(w)