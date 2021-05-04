package psbp.internal.implementation.computation.transformation.reading.givens

import psbp.external.specification.reading.Readable

import psbp.external.specification.materialization.Materialization

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

import psbp.internal.specification.computation.Computation

private[psbp] given readingTransformedMaterialization[
  R: Readable
  , C[+ _]: Computation
          : [C[+ _]] =>> Materialization[[Z, Y] =>> Z => C[Y], Z, Y]
  , Z, Y
]: Materialization[[Z, Y] =>> Z => ReadingTransformed[R, C][Y], Z, R ?=> C[Y]] with

  private type F[+Z] = C[Z]
  private type T[+Z] = ReadingTransformed[R, C][Z]

  private type `=>F`[-Z, +Y] = Z => F[Y]
  private type `=>T`[-Z, +Y] = Z => T[Y]

  private val materialization = summon[Materialization[`=>F`, Z, Y]]
  import materialization.{ 
    materialize => materializeF 
  }

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
    , bind => bindF 
  }

  override val materialize: (Unit `=>T` Unit) => Z ?=> (R ?=> C[Y]) =
    `u=>tu` =>
      bindF(
        `u=>tu`(())
        , _ => 
            resultF(materializeF(resultF))
      )
  
