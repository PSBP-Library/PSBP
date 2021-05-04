package psbp.external.implementation.stdIn.givens

import scala.language.postfixOps

import psbp.external.specification.program.Program

import psbp.external.specification.reading.{
  Readable
  , ConvertibleFromReadable
}

import psbp.external.implementation.stdIn.StdIn

given convertibleFromStdInReadable[
  Z: [Z] =>> Readable[StdIn[Z]]
  , >-->[- _, + _]: Program
]: ConvertibleFromReadable[StdIn[Z], Z, >-->] with

  override def convert: StdIn[Z] >--> Z =
    object function {
      val convert: StdIn[Z] => Z =
        stdIn => 
          stdIn.`u=>z`(())
    } 

    function.convert asProgram
