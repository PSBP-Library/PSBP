package psbp.external.specification.program.reading.givens

import psbp.external.specification.program.Program

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.specification.program.reading.ProgramWithReading

given programWithReading[
  R: Readable, 
  >-->[- _, + _]: Program
                : [>-->[- _, + _]] =>> Reading[R, >-->]
]: ProgramWithReading[R, >-->] with
 
  private val program: Program[>-->] = 
    summon[Program[>-->]]

  export program.identity
  export program.andThen

  export program.toProgram
  export program.construct
  export program.conditionally

  private val reading: Reading[R, >-->] = 
    summon[Reading[R, >-->]]

  export reading.read