package psbp.external.implementation.active.writing.reading

import psbp.external.specification.program.Program

import psbp.external.specification.writing.Writable

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.{
  ActiveWritingReading
  , `=>AWR`
}

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

import psbp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
}

import psbp.internal.implementation.computation.transformation.reading.readingTransformedComputation

private[psbp] given activeWritingReadingComputation[
  W: Writable
  , R
]: Computation[ActiveWritingReading[W, R]] = 
  readingTransformedComputation[R, ActiveWriting[W]]

import psbp.external.implementation.computation.programFromComputation

given activeWritingReadingProgram[
  W: Writable
  , R
]: Program[`=>AWR`[W, R]] = 
  programFromComputation[ActiveWritingReading[W, R]]