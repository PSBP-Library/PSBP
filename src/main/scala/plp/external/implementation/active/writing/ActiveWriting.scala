package plp.external.implementation.active.writing

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.implementation.computation.transformation.writing.WritingTransformed

import plp.external.implementation.active.Active

type ActiveWriting[W] = [Y] =>> WritingTransformed[W, Active][Y] 

type `=>AW`[W] = [Z, Y] =>> ProgramFromComputation[ActiveWriting[W]][Z, Y]