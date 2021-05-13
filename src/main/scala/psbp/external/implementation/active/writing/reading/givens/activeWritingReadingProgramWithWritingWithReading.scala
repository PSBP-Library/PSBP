package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.program.Program

import psbp.external.specification.reading.Readable

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.implementation.active.writing.reading.{
  // ActiveWritingReading
  `=>AWR`// import psbp.external.implementation.stdIn.StdIn

}

import psbp.external.specification.program.writing.reading.ProgramWithWritingWithReading

import psbp.external.specification.program.writing.reading.givens.programWithWritingWithReading

given activeWritingReadingProgramWithWritingWithReading[
  W: Writable
  , R: Readable
]: ProgramWithWritingWithReading[W, R, `=>AWR`[W, R]] = 
  programWithWritingWithReading[W, R, `=>AWR`[W, R]]