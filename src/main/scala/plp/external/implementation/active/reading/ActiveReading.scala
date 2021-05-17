package plp.external.implementation.active.reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.implementation.computation.transformation.reading.ReadingTransformed

import plp.external.implementation.active.Active

type ActiveReading[R] = [Y] =>> ReadingTransformed[R, Active][Y] 

type `=>AR`[R] = [Z, Y] =>> ProgramFromComputation[ActiveReading[R]][Z, Y]