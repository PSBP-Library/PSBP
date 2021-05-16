package psbp.external.implementation.active.writing.givens

import psbp.external.specification.program.Program

import psbp.external.specification.writing.Writable

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.internal.implementation.computation.transformation.writing.givens.writingTransformedComputation

private[psbp] given activeWritingComputation[W: Writable]: Computation[ActiveWriting[W]] = 
  writingTransformedComputation[W, Active]

// import psbp.external.implementation.active.writing.`=>AW`
  
// givens

// import psbp.external.implementation.computation.givens.programFromComputation

// given activeWritingProgram[W: Writable]: Program[`=>AW`[W]] =
//   programFromComputation[ActiveWriting[W]]