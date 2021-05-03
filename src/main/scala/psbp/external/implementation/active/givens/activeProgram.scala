package psbp.external.implementation.active.givens

import psbp.external.specification.program.Program

import psbp.internal.specification.computation.Computation

import psbp.external.implementation.computation.givens.programFromComputation

import psbp.external.implementation.active.{
  Active
  , `=>A`
}

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