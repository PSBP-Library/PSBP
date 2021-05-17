package plp.external.implementation.stdOut

import scala.language.postfixOps

import plp.external.specification.program.Program

import plp.external.specification.writing.Writing

given [>-->[- _, + _]: Program]: Writing[StdOut, >-->] with

  override def write: StdOut >--> Unit =

    object function {

      val write: StdOut => Unit =
        case StdOut(effect) =>
          effect(())
    }

    function.write asProgram  

