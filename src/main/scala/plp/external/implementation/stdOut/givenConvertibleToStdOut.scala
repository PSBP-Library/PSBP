package plp.external.implementation.stdOut

import scala.language.postfixOps

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.writing.ToString

import plp.external.specification.writing.{
  ConvertibleToWritable
}

import plp.external.implementation.stdOut.StdOut

given givenConvertibleToStdOut[
  Z: ToString
  , >-->[- _, + _]: Program
] : ConvertibleToWritable[Z, StdOut, >-->] with

  private val toString: ToString[Z] = summon[ToString[Z]]
  import toString._toString

  override private[plp] def convert: Z >--> StdOut =

    object function {

      val convert: Z => StdOut =
        z =>
          StdOut{ _ =>
            println(_toString(z))
          }

    }

    function.convert asProgram