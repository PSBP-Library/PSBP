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
  import computation.`i?~>c`

  private type `=>C`[-Z, +Y] = ProgramFromComputation[C][Z, Y]

  // defined

  override def identity[Z]: Z `=>C` Z =
      `i?~>c`.apply // (summon[Z])

  override def andThen[Z, Y, X](
    `z>-->y`: Z `=>C` Y
    , `y>-->x`: => Y `=>C` X): Z `=>C` X =
      given cy: C[Y] = `z>-->y` 
      cy >= {
        y =>
        given gy: Y = y
        `y>-->x`
      }      

  override def toProgram[Z, Y]: (Z => Y) => (Z `=>C` Y) = 
    `z=>y` => 
      given Y = `z=>y`(summon[Z])
      `i?~>c`.apply // (`z=>y`(summon[Z]))

  override def construct[Z, Y, X](
      `z>-->y`: Z `=>C` Y
      , `z>-->x`: => Z `=>C` X): Z `=>C` (Y && X) =
    given cy: C[Y] = `z>-->y`
    cy >= {
      y =>
      // given gy: Y = y
      // given cx: C[X] = `z>-->x`
      `z>-->x` >= {
        x =>
        // given gx: X = x
        given (Y, X) = (y, x)
        `i?~>c`.apply // (gy, gx)
      }
    }

  override def conditionally[Z, Y, X](
      `y>-->z`: => Y `=>C` Z, 
      `x>-->z`: => X `=>C` Z): (Y || X) `=>C` Z =
    foldSum({
      (y: Y) => 
        given gy: Y = y
        `y>-->z`
    }, {
      (x: X) =>
        given gx: X = x 
        `x>-->z`
      }
      )(summon[Y || X]) 