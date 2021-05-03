package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.specification.reading.Readable

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.`=>AWR`

// givens

import psbp.internal.implementation.computation.transformation.writing.reading.givens.readingTransformedWriting

import psbp.external.implementation.active.writing.givens.activeWritingWriting

given activeWritingReadingWriting[
  W: Writable
  , R: Readable
]: Writing[W, `=>AWR`[W, R]] = 
  readingTransformedWriting[R, W, ActiveWriting[W]]