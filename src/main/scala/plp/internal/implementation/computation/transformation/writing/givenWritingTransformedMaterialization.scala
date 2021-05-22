package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.writing.Writable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenWritingTransformedMaterialization[
  W: Writable
  , D[+ _]: Computation
          : [D[+ _]] =>> Materialization[
            ProgramFromComputation[D]
            , Z
            , Y
            ]
  , Z, Y
]: Materialization[
  ProgramFromComputation[WritingTransformed[W, D]]
  , Z
  , D[(W, Y)]
] with

  private type C[+Z] = WritingTransformed[W, D][Z]

  private type `=>D`= [Z, Y] =>> ProgramFromComputation[D][Z, Y]
  private type `=>C`= [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val Materialization = summon[Materialization[`=>D`, Z, Y]]
  import Materialization.{ 
    materialize => materializeF 
  }

  private val computation = summon[Computation[D]]
  import computation.{ 
    result => resultD
    , bind => bindD 
  }  

  override val materialize: (Unit `=>C` Unit) => Z ?=> D[(W, Y)] =
    `u=>cu` =>
      given Unit = ()
      bindD(
        `u=>cu`
        , (w, _) =>
          val y = materializeF(resultD)
          given (W, Y) = (w, y)
          resultD
      )


