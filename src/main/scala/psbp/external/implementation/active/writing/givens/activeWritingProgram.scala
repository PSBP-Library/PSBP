package psbp.external.implementation.active.writing.givens

import psbp.external.specification.program.Program

import psbp.external.specification.writing.Writable

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.{
    ActiveWriting, `=>AW`
}

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.computation.givens.programFromComputation

import psbp.external.implementation.active.givens.activeComputation

import psbp.internal.implementation.computation.transformation.writing.givens.writingTransformedComputation

private[psbp] given activeWritingComputation[W: Writable]: Computation[ActiveWriting[W]] = 
  writingTransformedComputation[W, Active]

given activeWritingProgram[W: Writable]: Program[`=>AW`[W]] =
  programFromComputation[ActiveWriting[W]]