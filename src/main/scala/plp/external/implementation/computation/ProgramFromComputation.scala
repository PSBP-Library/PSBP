package plp.external.implementation.computation

private[plp] type ProgramFromComputation[C[+ _]] = [Z, Y] =>> Z ?=> C[Y]


