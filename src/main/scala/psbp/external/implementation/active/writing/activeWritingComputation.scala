package psbp.external.implementation.active.writing

import psbp.external.specification.program.Program

import psbp.external.specification.writing.Writable

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.internal.specification.computation.Computation

import psbp.external.implementation.active.{
  given Computation[Active]
}

import psbp.internal.implementation.computation.transformation.writing.writingTransformedComputation

private[psbp] given [W: Writable]: Computation[ActiveWriting[W]] = 
  writingTransformedComputation[W, Active]

// import psbp.external.implementation.active.writing.`=>AW`
  
// import psbp.external.implementation.computation.programFromComputation

// given activeWritingProgram[W: Writable]: Program[`=>AW`[W]] =
//   programFromComputation[ActiveWriting[W]]