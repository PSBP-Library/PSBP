package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.program.Program

import psbp.external.specification.writing.Writable

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.{
  ActiveWritingReading
  , `=>AWR`
}

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.computation.givens.programFromComputation

import psbp.external.implementation.active.writing.givens.activeWritingComputation

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedComputation

private[psbp] given activeWritingReadingComputation[
  W: Writable
  , R
]: Computation[ActiveWritingReading[W, R]] = 
  readingTransformedComputation[R, ActiveWriting[W]]

given activeWritingReadingProgram[
  W: Writable
  , R
]: Program[`=>AWR`[W, R]] = 
  programFromComputation[ActiveWritingReading[W, R]]