package psbp.external.implementation.active

import psbp.external.implementation.computation.ProgramFromComputation

type Active[+Y] = Y

type `=>A`[-Z, +Y] = ProgramFromComputation[Active][Z, Y]
