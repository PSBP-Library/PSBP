package plp.external.implementation.active

import plp.external.specification.program.Program

import plp.external.implementation.active.Active

import plp.internal.specification.contextNaturalTransformation.?~>

import plp.internal.specification.computation.Computation

private[plp] given Computation[Active] with

  private[plp] def `i?~>c`: I ?~> Active =
    new {
      override private[plp] def apply[Z]: I[Z] ?=> Active[Z] =
        summon[Z]
    }

  override private[plp] def binding[Z, Y]: Active[Z] ?=> (Z ?=> Active[Y]) => Active[Y] =
    identity  
    
  // override private[plp] def bind[Z, Y](
  //   cz: Active[Z]
  //   , `z=>cy`: => Z => Active[Y]
  // ): Active[Y] =
  //   `z=>cy`(cz)

// import plp.external.implementation.computation.programFromComputation

// given activeProgram: Program[`=>A`] = 
//   programFromComputation[Active]