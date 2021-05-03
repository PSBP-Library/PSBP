package psbp.external.implementation.active.givens

import psbp.external.specification.program.Program

import psbp.external.implementation.active.{
  Active
  , `=>A`
}

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.computation.givens.programFromComputation

private[psbp] given activeComputation: Computation[Active] with

  private[psbp] def result[Z]: Z => Active[Z] =
    z =>
      z

  private[psbp] def bind[Z, Y](
    cz: Active[Z]
    , `z=>cy`: => Z => Active[Y]
  ): Active[Y] =
    `z=>cy`(cz)

given activeProgram: Program[`=>A`] = 
  programFromComputation[Active]