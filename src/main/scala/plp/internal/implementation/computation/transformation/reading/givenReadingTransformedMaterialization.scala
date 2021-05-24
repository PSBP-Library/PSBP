package plp.internal.implementation.computation.transformation.reading

import plp.external.specification.materialization.Materialization

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenReadingTransformedMaterialization[
  R
  , D[+ _]: Computation
          : [D[+ _]] =>> Materialization[
            ProgramFromComputation[D]
            , Z
            , Y
            ]
  , Z, Y
]: Materialization[
  ProgramFromComputation[ReadingTransformed[R, D]]
  , Z, 
  R ?=> D[Y]
] with

  private type C[+Z] = ReadingTransformed[R, D][Z]

  private type `?=>D`= [Z, Y] =>> ProgramFromComputation[D][Z, Y]
  private type `?=>C`= [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val materialization = summon[Materialization[`?=>D`, Z, Y]]
  import materialization.{ 
    materialize => materializeD 
  }

  private val computation = summon[Computation[D]]
  import computation.{ 
    cResult => cResultD
    , result => resultD
    , binding => bindingD 
  }

  // override val materialize: (Unit `?=>C` Unit) => Z ?=> (R ?=> D[Y]) =
  //   `u?=>cu` =>
  //     given u: Unit = ()
  //     given D[Unit] = `u?=>cu`
  //     bindingD{
  //       given Y = materializeD(resultD)
  //       resultD
  //     }  
  
  // import plp.external.implementation.toFunction

  override val materialize: (Unit `?=>C` Unit) => Z ?=> (R ?=> D[Y]) =
    `u?=>cu` =>
      given u: Unit = ()
      given D[Unit] = `u?=>cu`
      bindingD(resultD(materializeD(cResultD)))
       