package psbp.external.implementation.active.writing.reading

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.ActiveWriting

type ActiveWritingReading[W, R] = [Y] =>> ReadingTransformed[R, ActiveWriting[W]][Y]

type `=>AWR`[W, R] = [Z, Y] =>> Z => ActiveWritingReading[W, R][Y]