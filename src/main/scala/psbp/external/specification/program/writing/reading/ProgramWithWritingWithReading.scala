package psbp.external.specification.program.writing.reading

import psbp.external.specification.program.Program

import psbp.external.specification.writing.Writable

import psbp.external.specification.program.writing.ProgramWithWriting

import psbp.external.specification.reading.Reading

trait ProgramWithWritingWithReading[
  W: Writable
  , R
  , >-->[- _, + _]] 
  extends ProgramWithWriting[W, >-->] 
  , Reading[R, >-->]