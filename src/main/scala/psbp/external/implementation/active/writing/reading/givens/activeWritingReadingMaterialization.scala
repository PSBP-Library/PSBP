package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.{
  ActiveWritingReading
  , `=>AWR`
}

// givens

import psbp.external.implementation.active.writing.givens.activeWritingComputation

import psbp.external.implementation.active.writing.givens.activeWritingMaterialization 

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedMaterialization

given activeWritingReadingMaterialization[
  W: Writable
  , R
]: Materialization[
  `=>AWR`[W, R]
  , Unit
  , R ?=> (W, (W, Unit))
] = readingTransformedMaterialization[R, ActiveWriting[W], Unit, (W, Unit)]
