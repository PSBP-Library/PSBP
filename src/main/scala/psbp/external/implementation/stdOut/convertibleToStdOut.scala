package psbp.external.implementation.stdOut

import scala.language.postfixOps

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.writing.{
  ConvertibleToWritable
}

import psbp.external.implementation.stdOut.StdOut

// todo: perhaps get rid of using via type class AsMessage

given convertibleToStdOut[
  Z
  , >-->[- _, + _]: Program
](using message: Z => String)
  : ConvertibleToWritable[Z, StdOut, >-->] with

  override def convert: Z >--> StdOut =

    object function {

      val convert: Z => StdOut =
        z =>
          StdOut{ _ =>
            println(message(z))
          }

    }

    function.convert asProgram    