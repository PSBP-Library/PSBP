// package psbp.external.specification.program.writing.reading.givens

// import psbp.external.specification.program.Program

// import psbp.external.specification.writing.Writable

// import psbp.external.specification.writing.Writing

// import psbp.external.specification.reading.Reading

// import psbp.external.specification.program.writing.reading.ProgramWithWritingWithReading

// given programWithWritingWithReading[
//   W: Writable
//   , R
//   , >-->[- _, + _]: Program
//                   : [>-->[- _, + _]] =>> Writing[W, >-->]
//                   : [>-->[- _, + _]] =>> Reading[R, >-->]
// ]: ProgramWithWritingWithReading[W, R, >-->] with
 
//   private val program: Program[>-->] = summon[Program[>-->]]

//   export program.identity
//   export program.andThen

//   export program.toProgram
//   export program.construct
//   export program.conditionally

//   private val writing: Writing[W, >-->] = summon[Writing[W, >-->]]

//   export writing.write

//   private val reading: Reading[R, >-->] = summon[Reading[R, >-->]]

//   export reading.read
