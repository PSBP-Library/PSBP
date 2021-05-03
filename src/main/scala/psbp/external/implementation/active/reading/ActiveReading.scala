package psbp.external.implementation.active.reading

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

import psbp.external.implementation.active.Active

type ActiveReading[R] = [Y] =>> ReadingTransformed[R, Active][Y] 

type `=>AR`[R] = [Z, Y] =>> Z => ActiveReading[R][Y]