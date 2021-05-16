package psbp.external.implementation.stdOut

import scala.language.postfixOps

import psbp.external.specification.program.Program

// import psbp.external.implementation.stdOut.StdOut

import psbp.external.specification.writing.Writing

given stdOutWriting[>-->[- _, + _]: Program]: Writing[StdOut, >-->] with

  override def write: StdOut >--> Unit =

    object function {

      val write: StdOut => Unit =
        case StdOut(effect) =>
          effect(())
    }

    function.write asProgram  

