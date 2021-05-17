package psbp.external.implementation.stdOut

import scala.language.postfixOps

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.writing.{
  ConvertibleToWritable
}

import psbp.external.implementation.stdOut.StdOut

given convertibleToStdOut[
  Z: AsMessage
  , >-->[- _, + _]: Program
] : ConvertibleToWritable[Z, StdOut, >-->] with

  private val asMessage: AsMessage[Z] = summon[AsMessage[Z]]
  import asMessage.message

  override def convert: Z >--> StdOut =

    object function {

      val convert: Z => StdOut =
        z =>
          StdOut{ _ =>
            println(message(z))
          }

    }

    function.convert asProgram