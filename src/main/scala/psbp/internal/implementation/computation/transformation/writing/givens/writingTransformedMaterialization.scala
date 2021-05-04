package psbp.internal.implementation.computation.transformation.writing.givens

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

import psbp.internal.specification.computation.Computation

private[psbp] given writingTransformedMaterialization[
  W: Writable
  , C[+ _]: Computation
          : [C[+ _]] =>> Materialization[[Z, Y] =>> Z => C[Y], Z, Y]
  , Z, Y
]: Materialization[[Z, Y] =>> Z => WritingTransformed[W, C][Y], Z, C[(W, Y)]] with

  private type F[+Z] = C[Z]
  private type T[+Z] = WritingTransformed[W, C][Z]

  private type `=>F`[-Z, +Y] = Z => F[Y]
  private type `=>T`[-Z, +Y] = Z => T[Y]

  private val Materialization = summon[Materialization[`=>F`, Z, Y]]
  import Materialization.{ 
    materialize => materializeF 
  }

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
    , bind => bindF 
  }  

  override val materialize: (Unit `=>T` Unit) => Z ?=> C[(W, Y)] =
    `u=>tu` =>
      bindF(
        `u=>tu`(())
        , (w, u) =>
            resultF((w, materializeF(resultF)))
      )
