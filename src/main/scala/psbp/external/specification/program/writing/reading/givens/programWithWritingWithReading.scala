package psbp.external.specification.program.writing.reading.givens

import psbp.external.specification.program.Program

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.specification.writing.{
  Writable
  // , Writing
}

import psbp.external.specification.program.writing.ProgramWithWriting

import psbp.external.specification.program.writing.reading.ProgramWithWritingWithReading

given programWithWritingWithReading[
  W: Writable
  , R: Readable
  , >-->[- _, + _]: [>-->[- _, + _]] =>> ProgramWithWriting[W, >-->]
                  : [>-->[- _, + _]] =>> Reading[R, >-->]
]: ProgramWithWritingWithReading[W, R, >-->] with
 
  private val programWithWriting: ProgramWithWriting[W, >-->] = summon[ProgramWithWriting[W, >-->]]

  export programWithWriting.identity
  export programWithWriting.andThen

  export programWithWriting.toProgram
  export programWithWriting.construct
  export programWithWriting.conditionally

  export programWithWriting.write

  private val reading: Reading[R, >-->] = summon[Reading[R, >-->]]

  export reading.read
