package psbp.external.implementation.active.reading

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

import psbp.external.implementation.active.Active

type ActiveReading[R] = [Y] =>> ReadingTransformed[R, Active][Y] 

type `=>AR`[R] = [Z, Y] =>> ProgramFromComputation[ActiveReading[R]][Z, Y]