package plp.external.implementation.active.writing

import plp.external.specification.writing.Writable

import plp.external.implementation.active.Active

import plp.external.implementation.active.writing.ActiveWriting

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.{
  given Computation[Active]
}

import plp.internal.implementation.computation.transformation.writing.givenWritingTransformedComputation

private[plp] given [W: Writable]: Computation[ActiveWriting[W]] = 
  givenWritingTransformedComputation[W, Active]

// import plp.external.implementation.active.writing.`=>AW`
  
// import plp.external.implementation.computation.programFromComputation

// given activeWritingProgram[W: Writable]: Program[`=>AW`[W]] =
//   programFromComputation[ActiveWriting[W]]