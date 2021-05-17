package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.writing.Writable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenWritingTransformedMaterialization[
  W: Writable
  , C[+ _]: Computation
          : [C[+ _]] =>> Materialization[
            ProgramFromComputation[C]
            , Z
            , Y
            ]
  , Z, Y
]: Materialization[
  ProgramFromComputation[WritingTransformed[W, C]]
  , Z
  , C[(W, Y)]
] with

  private type F[+Z] = C[Z]
  private type T[+Z] = WritingTransformed[W, C][Z]

  private type `=>F`= [Z, Y] =>> ProgramFromComputation[F][Z, Y]
  private type `=>T`= [Z, Y] =>> ProgramFromComputation[T][Z, Y]

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
            val y = materializeF(resultF)
            resultF((w, y))
      )


