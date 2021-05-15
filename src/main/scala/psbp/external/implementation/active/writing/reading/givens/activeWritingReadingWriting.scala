package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.`=>AWR`

// givens

import psbp.external.implementation.active.writing.givens.activeWritingWriting

import psbp.internal.implementation.computation.transformation.writing.reading.givens.readingTransformedWriting

given activeWritingReadingWriting[
  W: Writable
  , R
]: Writing[W, `=>AWR`[W, R]] = 
  readingTransformedWriting[R, W, ActiveWriting[W]]