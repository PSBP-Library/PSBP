package psbp.external.specification.program.writing.reading

import psbp.external.specification.program.Program

import psbp.external.specification.writing.Writable

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.specification.program.writing.ProgramWithWriting

trait ProgramWithWritingWithReading[
  W: Writable
  , R: Readable
  , >-->[- _, + _]] 
  extends ProgramWithWriting[W, >-->] 
  , Reading[R, >-->]