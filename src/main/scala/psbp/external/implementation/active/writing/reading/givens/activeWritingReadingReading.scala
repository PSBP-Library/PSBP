package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.writing.Writable

import psbp.external.specification.reading.Reading

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.`=>AWR`

// givens

import psbp.external.implementation.active.writing.givens.activeWritingComputation

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedReading

given activeWritingReadingReading[
  W: Writable
  , R
]: Reading[R, `=>AWR`[W, R]] = 
  readingTransformedReading[R, ActiveWriting[W]]