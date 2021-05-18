package plp.external.implementation.computation

import plp.external.specification.types.{ 
  &&
  , ||
}

import plp.external.specification.function.foldSum

import plp.external.specification.program.Program

import plp.internal.specification.computation.Computation

private[plp] given givenProgramFromComputation[
  C[+ _]: Computation
]: Program[ProgramFromComputation[C]] with
  
  private val computation = 
    summon[Computation[C]]
  import computation.`i~>c`

  private type `=>C`[-Z, +Y] = ProgramFromComputation[C][Z, Y]

  // defined

  override def identity[Z]: Z `=>C` Z =
    `i~>c`.apply

  override def andThen[Z, Y, X](
    `z>-->y`: Z `=>C` Y
    , `y>-->x`: => Y `=>C` X): Z `=>C` X =
    z =>
      `z>-->y`(z) >= 
        `y>-->x`      

  override def toProgram[Z, Y]: (Z => Y) => (Z `=>C` Y) = 
    `z=>y` => 
      z =>
        `i~>c`(`z=>y`(z))

  override def construct[Z, Y, X](
      `z>-->y`: Z `=>C` Y
      , `z>-->x`: => Z `=>C` X): Z `=>C` (Y && X) =
    z =>
      `z>-->y`(z) >= { y => 
        `z>-->x`(z) >= { x =>
          `i~>c`(y, x)
        }
      }

  override def conditionally[Z, Y, X](
      `y>-->z`: => Y `=>C` Z, 
      `x>-->z`: => X `=>C` Z): (Y || X) `=>C` Z =
    foldSum(`y>-->z`, `x>-->z`) 