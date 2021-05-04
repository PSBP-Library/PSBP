package psbp.external.implementation.stdIn.givens

import scala.language.postfixOps

import psbp.external.specification.program.Program

import psbp.external.specification.program.reading.ProgramWithReading

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.implementation.stdIn.StdIn

given stdInProgramWithReading[
  Z: [Z] =>> Readable[StdIn[Z]],
  >-->[- _, + _]: Program
]: ProgramWithReading[StdIn[Z], >-->] with

  private val program = summon[Program[>-->]]

  export program.identity
  export program.andThen

  export program.toProgram
  export program.construct
  export program.conditionally

  private type R = StdIn[Z]

  override def read: Unit >--> R = 

    object function {

      val read: Unit => R =
        _ =>
          val readable = summon[Readable[StdIn[Z]]]
          readable.r

    }

    function.read asProgram 


