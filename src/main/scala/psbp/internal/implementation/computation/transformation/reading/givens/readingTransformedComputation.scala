package psbp.internal.implementation.computation.transformation.reading.givens

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

import psbp.internal.specification.naturalTransformation.~>

import psbp.internal.specification.computation.transformation.ComputationTransformation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedComputation[
  R
  , C[+ _]: Computation
]: ComputationTransformation[C, ReadingTransformed[R, C]] 
  with Computation[ReadingTransformed[R, C]] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    bind => bindF
  }

  override private[psbp] val `f~>t`: F ~> T = 
    new {
      def apply[Z]: F[Z] => T[Z] =
        fz => 
          fz
    }  

  override private[psbp] def bind[Z, Y](
    tz: T[Z]
    , `z=>ty`: => Z => T[Y]
  ): T[Y] =
    bindF(tz, z => `z=>ty`(z))
