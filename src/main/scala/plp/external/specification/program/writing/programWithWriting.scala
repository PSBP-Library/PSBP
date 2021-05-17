// package plp.external.specification.program.writing.givens

// import plp.external.specification.program.Program

// import plp.external.specification.writing.{
//   Writable
//   , Writing
// }

// import plp.external.specification.program.writing.ProgramWithWriting

// given programWithWriting[
//   W: Writable, 
//   >-->[- _, + _]: Program
//                 : [>-->[- _, + _]] =>> Writing[W, >-->]
// ]: ProgramWithWriting[W, >-->] with
 
//   private val program: Program[>-->] = summon[Program[>-->]]

//   export program.identity
//   export program.andThen

//   export program.toProgram
//   export program.construct
//   export program.conditionally

//   private val writing: Writing[W, >-->] = summon[Writing[W, >-->]]

//   export writing.write