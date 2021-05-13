package psbp.external.implementation.stdOut.givens

import scala.language.postfixOps

import psbp.external.specification.program.Program

import psbp.external.specification.program.writing.ProgramWithWriting

import psbp.external.implementation.stdOut.StdOut

// given stdOutProgramWithWriting[>-->[- _, + _]: Program]: ProgramWithWriting[StdOut, >-->] with

//   private val program: Program[>-->] = summon[Program[>-->]]

//   export program.identity
//   export program.andThen

//   export program.toProgram
//   export program.construct
//   export program.conditionally

//   override def write: StdOut >--> Unit =

//     object function {

//       val write: StdOut => Unit =
//         case StdOut(effect) =>
//           effect(())

//     }

//     function.write asProgram  

// below used ???

import psbp.external.specification.writing.Writing

given stdOutWriting[>-->[- _, + _]: Program]: Writing[StdOut, >-->] with

  // private val program: Program[>-->] = summon[Program[>-->]]

  // export program.identity
  // export program.andThen

  // export program.toProgram
  // export program.construct
  // export program.conditionally

  override def write: StdOut >--> Unit =

    object function {

      val write: StdOut => Unit =
        case StdOut(effect) =>
          println("WRITE")
          effect(())

    }

    function.write asProgram  

