package plp.internal.implementation.computation.transformation.reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.contextNaturalTransformation.?~>

import plp.internal.specification.transformation.Transformation

private[plp] given givenReadingTransformedComputation[
  R
  , D[+ _]: Computation
]: Transformation[
  D
  , ReadingTransformed[R, D]
] with Computation[ReadingTransformed[R, D]] with

  private type C[+Y] = ReadingTransformed[R, D][Y]

  private type `=>C` = [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    bind => bindD
  }

  override private[plp] val `d?~>c`: D ?~> C = 
    new {
      def apply[Z]: D[Z] ?=> C[Z] =
        summon[D[Z]]
    }  

  override private[plp] def bind[Z, Y](
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y] =
    bindD(cz, z => `z=>cy`(z))
