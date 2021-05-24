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

  private type `?=>D`= [Z, Y] =>> ProgramFromComputation[D][Z, Y]
  private type `?=>C`= [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val Materialization = summon[Materialization[`?=>D`, Z, Y]]
  import Materialization.{ 
    materialize => materializeD
  }

  private val computation = summon[Computation[D]]
  import computation.{ 
    cResult => cResultD
    , result => resultD
    , binding => bindingD 
  }  

  // override val materialize: (Unit `?=>C` Unit) => Z ?=> D[(W, Y)] =
  //   `u?=>cu` =>
  //     given u: Unit = ()
  //     given D[(W, Unit)] = `u?=>cu`
  //     bindingD{
  //       val (w, u) = summon[(W, Unit)]
  //       given (W, Y) = (w, materializeD(resultD))
  //       resultD
  //     }  

  override val materialize: (Unit `?=>C` Unit) => Z ?=> D[(W, Y)] =
    `u?=>cu` =>
      given u: Unit = ()
      given D[(W, Unit)] = `u?=>cu`
      bindingD(resultD({ val (w, u) = summon[(W, Unit)] ; (w, materializeD(cResultD)) }))

      // given Unit = ()
      // bindD(
      //   `u=>cu`
      //   , (w, _) =>
      //     val y = materializeD(resultD)
      //     given (W, Y) = (w, y)
      //     resultD
      // )

  // override val materialize: (Unit `?=>C` Unit) => Z ?=> D[(W, Y)] =
  //   `u=>cu` =>
  //     given Unit = ()
  //     bindD(
  //       `u=>cu`
  //       , (w, _) =>
  //         val y = materializeD(resultD)
  //         given (W, Y) = (w, y)
  //         resultD
  //     )


