package psbp.external.implementation.active.writing.reading

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.`=>AW`

import psbp.external.implementation.active.writing.reading.`=>AWR`

import psbp.external.implementation.active.writing.{
  given Writing[?, `=>AW`[?]]
}

import psbp.internal.implementation.computation.transformation.writing.reading.readingTransformedWriting

given activeWritingReadingWriting[
  W: Writable
  , R
]: Writing[W, `=>AWR`[W, R]] = 
  readingTransformedWriting[R, W, ActiveWriting[W]]