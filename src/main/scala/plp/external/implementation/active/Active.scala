package plp.external.implementation.active

import plp.external.implementation.computation.ProgramFromComputation

type Active = [Y] =>> Y

type `=>A`= [Z, Y] =>> ProgramFromComputation[Active][Z, Y]
