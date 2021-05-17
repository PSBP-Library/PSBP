package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.types.&&

import plp.external.specification.writing.Writable

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.naturalTransformation.~>

import plp.internal.specification.computation.transformation.ComputationTransformation

private[plp] given givenWritingTransformedComputation[
  W : Writable
  , C[+ _]: Computation
]: ComputationTransformation[
  C
  , WritingTransformed[W, C]
] with Computation[WritingTransformed[W, C]] with 

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
    nothing
    , append
  }  

  override private[plp] val `f~>t`: F ~> T = new {
    def apply[Z]: F[Z] => T[Z] =
      fz =>
        bindF(fz, z => resultF((nothing, z)))
  }  

  override private[plp] def bind[Z, Y](
    tz: T[Z]
    , `z=>ty`: => Z => T[Y]
  ): T[Y] =
    bindF(tz, (w1, z) =>
      val (w2, y): W && Y = `z=>ty`(z)
      resultF(append(w1, w2), y)
    )
