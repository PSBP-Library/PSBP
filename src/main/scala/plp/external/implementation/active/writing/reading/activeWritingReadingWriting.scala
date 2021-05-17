package plp.external.implementation.active.writing.reading

import plp.external.specification.writing.{
  Writable
  , Writing
}

import plp.external.implementation.active.writing.ActiveWriting

import plp.external.implementation.active.writing.`=>AW`

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.active.writing.{
  given Writing[?, `=>AW`[?]]
}

import plp.internal.implementation.computation.transformation.writing.reading.givenReadingTransformedWriting

given [
  W: Writable
  , R
]: Writing[W, `=>AWR`[W, R]] = 
  givenReadingTransformedWriting[R, W, ActiveWriting[W]]