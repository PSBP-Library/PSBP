package plp.external.implementation.active.writing.reading

import plp.external.specification.program.Program

import plp.external.specification.writing.Writable

import plp.external.implementation.active.writing.ActiveWriting

import plp.external.implementation.active.writing.reading.{
  ActiveWritingReading
  , `=>AWR`
}

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
}

import plp.internal.implementation.computation.transformation.reading.givenReadingTransformedComputation

private[plp] given [
  W: Writable
  , R
]: Computation[ActiveWritingReading[W, R]] = 
  givenReadingTransformedComputation[R, ActiveWriting[W]]

import plp.external.implementation.computation.givenProgramFromComputation

given [
  W: Writable
  , R
]: Program[`=>AWR`[W, R]] = 
  givenProgramFromComputation[ActiveWritingReading[W, R]]