package psbp.external.implementation.active.writing

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.`=>AW`

import psbp.internal.specification.computation.Computation

import psbp.external.implementation.active.{
  given Computation[Active]
}

import psbp.internal.implementation.computation.transformation.writing.writingTransformedWriting

given activeWritingWriting[W: Writable]: Writing[W, `=>AW`[W]] = 
  writingTransformedWriting[W, Active]