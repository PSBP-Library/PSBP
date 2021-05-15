package psbp.internal.implementation.computation.transformation.writing.givens

import psbp.external.specification.types.&&

import psbp.external.specification.writing.Writable

import psbp.internal.specification.computation.Computation

import psbp.internal.specification.naturalTransformation.~>

import psbp.internal.specification.computation.transformation.ComputationTransformation

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

private[psbp] given writingTransformedComputation[
  W : Writable
  , C[+ _]: Computation
]: ComputationTransformation[C,  WritingTransformed[W, C]] 
  with Computation[WritingTransformed[W, C]] with 

  private type F[+Y] = C[Y]
  private type T[+Y] = WritingTransformed[W, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
    , bind => bindF
  }

  private val writable = summon[Writable[W]]
  import writable.{
    empty
    , append
  }  

  override private[psbp] val `f~>t`: F ~> T = new {
    def apply[Z]: F[Z] => T[Z] =
      fz =>
        bindF(fz, z => resultF((empty, z)))
  }  

  override private[psbp] def bind[Z, Y](
    tz: T[Z]
    , `z=>ty`: => Z => T[Y]
  ): T[Y] =
    bindF(tz, (w1, z) =>
      val (w2, y): W && Y = `z=>ty`(z)
      resultF(append(w1, w2), y)
    )
