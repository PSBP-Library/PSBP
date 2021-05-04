package psbp.external.specification.program.reading.givens

import scala.language.postfixOps

import psbp.external.specification.program.Program

import psbp.external.specification.reading.Readable

import psbp.external.specification.program.reading.ProgramWithReading

given programWithReading[
  R: Readable, 
  >-->[- _, + _]: Program
]: ProgramWithReading[R, >-->] with
 
  private val program: Program[>-->] = summon[Program[>-->]]

  export program.identity
  export program.andThen

  export program.toProgram
  export program.construct
  export program.conditionally

  override def read: Unit >--> R = 

    object function {

      val read: Unit => R =
        _ =>
          val readable = summon[Readable[R]]
          readable.r

    }

    function.read asProgram 
