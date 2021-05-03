package psbp.external.implementation.active.writing.givens

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.`=>AW`

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.internal.implementation.computation.transformation.writing.givens.writingTransformedWriting

given activeWritingWriting[W: Writable]: Writing[W, `=>AW`[W]] = 
  writingTransformedWriting[W, Active]