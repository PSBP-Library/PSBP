package psbp.external.implementation.computation

private[psbp] type ProgramFromComputation[C[+ _]] = [Z, Y] =>> Z => C[Y]


