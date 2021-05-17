package plp.external.implementation.active

import plp.external.specification.program.Program

import plp.external.implementation.active.Active

import plp.internal.specification.computation.Computation

private[plp] given Computation[Active] with

  private[plp] def result[Z]: Z => Active[Z] =
    z =>
      z

  private[plp] def bind[Z, Y](
    cz: Active[Z]
    , `z=>cy`: => Z => Active[Y]
  ): Active[Y] =
    `z=>cy`(cz)

// import plp.external.implementation.computation.programFromComputation

// given activeProgram: Program[`=>A`] = 
//   programFromComputation[Active]