package plp.external.implementation.computation

import plp.external.specification.types.{ 
  &&
  , ||
}

import plp.external.specification.function.foldSum

import plp.external.specification.program.Program

import plp.external.implementation.toFunction

import plp.external.implementation.fromFunction

import plp.internal.specification.computation.Computation

private[plp] given givenProgramFromComputation[
  C[+ _]: Computation
]: Program[ProgramFromComputation[C]] with
  
  private val computation = 
    summon[Computation[C]]
  import computation.{
    cResult
    , result
  }

  private type `=>C`[-Z, +Y] = ProgramFromComputation[C][Z, Y]

  // defined

  override def id[Z]: Z `=>C` Z =
    cResult

  // private def resultFunction[Z]: Z => C[Z] =
  //   toFunction(cResult)
  
  override def andThen[Z, Y, X](
    `z>-->y`: Z `=>C` Y
    , `y>-->x`: => Y `=>C` X): Z `=>C` X =
      `z>-->y` >= 
        toFunction(`y>-->x`)

  override def toProgram[Z, Y]: (Z => Y) => (Z `=>C` Y) = 
    `z=>y` => 
      result(fromFunction(`z=>y`))

  override def construct[Z, Y, X](
      `z>-->y`: Z `=>C` Y
      , `z>-->x`: => Z `=>C` X): Z `=>C` (Y && X) =
    `z>-->y` >= { y =>
      `z>-->x` >= { x =>
        result((y, x))
      }
    }

  override def conditionally[Z, Y, X](
      `y>-->z`: => Y `=>C` Z, 
      `x>-->z`: => X `=>C` Z): (Y || X) `=>C` Z =
    foldSum(toFunction(`y>-->z`), toFunction(`x>-->z`))(summon[Y || X]) 
    