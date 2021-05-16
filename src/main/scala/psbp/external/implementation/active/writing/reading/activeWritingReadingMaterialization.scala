package psbp.external.implementation.active.writing.reading

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.`=>AW`

import psbp.external.implementation.active.writing.reading.`=>AWR`

import psbp.internal.specification.computation.Computation

import psbp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
}  

import psbp.external.implementation.active.writing.{
  given Materialization[`=>AW`[?], Unit, (?, Unit)]
}

import psbp.internal.implementation.computation.transformation.reading.readingTransformedMaterialization

given activeWritingReadingMaterialization[
  W: Writable
  , R
]: Materialization[
  `=>AWR`[W, R]
  , Unit
  , R ?=> (W, (W, Unit))
] = readingTransformedMaterialization[R, ActiveWriting[W], Unit, (W, Unit)]
