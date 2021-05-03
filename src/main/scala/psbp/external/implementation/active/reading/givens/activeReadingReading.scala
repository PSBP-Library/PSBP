package psbp.external.implementation.active.reading.givens

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.reading.`=>AR`

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedReading

given activeReadingReading[R: Readable]: Reading[R, `=>AR`[R]] = 
  readingTransformedReading[R, Active]