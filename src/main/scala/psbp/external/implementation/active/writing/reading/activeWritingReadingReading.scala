package psbp.external.implementation.active.writing.reading

import psbp.external.specification.writing.Writable

import psbp.external.specification.reading.Reading

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.`=>AWR`

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
} 

import psbp.internal.implementation.computation.transformation.reading.readingTransformedReading

given activeWritingReadingReading[
  W: Writable
  , R
]: Reading[R, `=>AWR`[W, R]] = 
  readingTransformedReading[R, ActiveWriting[W]]