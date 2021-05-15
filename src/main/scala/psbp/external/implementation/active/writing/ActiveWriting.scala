package psbp.external.implementation.active.writing

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

import psbp.external.implementation.active.Active

type ActiveWriting[W] = [Y] =>> WritingTransformed[W, Active][Y] 

type `=>AW`[W] = [Z, Y] =>> ProgramFromComputation[ActiveWriting[W]][Z, Y]