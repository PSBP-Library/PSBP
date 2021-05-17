package plp.internal.implementation.computation.transformation.reading

import plp.external.specification.materialization.Materialization

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenReadingTransformedMaterialization[
  R
  , C[+ _]: Computation
          : [C[+ _]] =>> Materialization[
            ProgramFromComputation[C]
            , Z
            , Y
            ]
  , Z, Y
]: Materialization[
  ProgramFromComputation[ReadingTransformed[R, C]]
  , Z, 
  R ?=> C[Y]
] with

  private type F[+Z] = C[Z]
  private type T[+Z] = ReadingTransformed[R, C][Z]

  private type `=>F`= [Z, Y] =>> ProgramFromComputation[F][Z, Y]
  private type `=>T`= [Z, Y] =>> ProgramFromComputation[T][Z, Y]

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
            val y = materializeF(resultF)
            resultF(y)
      )
  
