package psbp.external.implementation.stdOut.givens

import scala.language.postfixOps

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.writing.{
  ConvertibleToWritable
}

import psbp.external.implementation.stdOut.StdOut

given argumentAndResultConvertibleToStdOutWritable[
  Z
  , Y
  , >-->[- _, + _]: Program
](using message: (Z && Y) => String)
  : ConvertibleToWritable[(Z && Y), StdOut, >-->] with

  override def convert: (Z && Y) >--> StdOut =
    object function {

      val convert: (Z && Y) => StdOut =
        (z, y) =>
          StdOut{ _ =>
            println(message(z, y))
          }

    }

    function.convert asProgram
