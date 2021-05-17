package plp.external.implementation.active.writing.reading

import plp.external.specification.writing.Writable

import plp.external.specification.reading.Reading

import plp.external.implementation.active.writing.ActiveWriting

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
} 

import plp.internal.implementation.computation.transformation.reading.givenReadingTransformedReading

given [
  W: Writable
  , R
]: Reading[R, `=>AWR`[W, R]] = 
  givenReadingTransformedReading[R, ActiveWriting[W]]