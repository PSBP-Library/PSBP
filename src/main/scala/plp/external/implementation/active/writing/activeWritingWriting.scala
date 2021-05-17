package plp.external.implementation.active.writing

import plp.external.specification.writing.{
  Writable
  , Writing
}

import plp.external.implementation.active.Active

import plp.external.implementation.active.writing.`=>AW`

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.{
  given Computation[Active]
}

import plp.internal.implementation.computation.transformation.writing.givenWritingTransformedWriting

given [W: Writable]: Writing[W, `=>AW`[W]] = 
  givenWritingTransformedWriting[W, Active]