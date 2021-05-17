package plp.external.implementation.stdOut

import scala.language.postfixOps

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.writing.{
  ConvertibleToWritable
}

import plp.external.implementation.stdOut.StdOut

given givenConvertibleToStdOut[
  Z: ToMessage
  , >-->[- _, + _]: Program
] : ConvertibleToWritable[Z, StdOut, >-->] with

  private val toMessage: ToMessage[Z] = summon[ToMessage[Z]]
  import toMessage.message

  override private[plp] def convert: Z >--> StdOut =

    object function {

      val convert: Z => StdOut =
        z =>
          StdOut{ _ =>
            println(message(z))
          }

    }

    function.convert asProgram