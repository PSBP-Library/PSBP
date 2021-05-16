// package psbp.external.implementation

// import scala.language.postfixOps

// import psbp.external.specification.program.Program

// import psbp.external.specification.reading.Reading

// given reading[
//   Z
//   , >-->[- _, + _]: Program 
// ](using z: Z): Reading[Z, >-->] with

//   object function {
//     val read: Unit => Z =
//       _ => 
//         z
//   }

//   override def read: Unit >--> Z =
//     function.read asProgram