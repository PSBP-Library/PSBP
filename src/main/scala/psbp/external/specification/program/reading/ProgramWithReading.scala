package psbp.external.specification.program.reading

import psbp.external.specification.program.Program

import psbp.external.specification.reading.{
  Readable
  , Reading
}

trait ProgramWithReading[R: Readable, >-->[- _, + _]] 
  extends Program[>-->] 
  , Reading[R, >-->]