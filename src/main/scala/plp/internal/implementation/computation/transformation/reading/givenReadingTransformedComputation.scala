package plp.internal.implementation.computation.transformation.reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.naturalTransformation.~>

import plp.internal.specification.computation.transformation.ComputationTransformation

private[plp] given givenReadingTransformedComputation[
  R
  , C[+ _]: Computation
]: ComputationTransformation[
  C
  , ReadingTransformed[R, C]
] with Computation[ReadingTransformed[R, C]] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    bind => bindF
  }

  override private[plp] val `d~>c`: F ~> T = 
    new {
      def apply[Z]: F[Z] => T[Z] =
        fz => 
          fz
    }  

  override private[plp] def bind[Z, Y](
    tz: T[Z]
    , `z=>ty`: => Z => T[Y]
  ): T[Y] =
    bindF(tz, z => `z=>ty`(z))
