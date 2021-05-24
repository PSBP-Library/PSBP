package plp.internal.implementation.computation.transformation.reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.contextNaturalTransformation.?~>

import plp.internal.specification.transformation.Transformation

private[plp] given givenReadingTransformedComputation[
  R
  , D[+ _]: Computation
]: Transformation[
  D
  , ReadingTransformed[R, D]
] with Computation[ReadingTransformed[R, D]] with

  private type C[+Y] = ReadingTransformed[R, D][Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    binding => bindingD
  }

  override private[plp] val `d?~>c`: D ?~> C = 
    new {
      def apply[Z]: D[Z] ?=> C[Z] =
        summon[D[Z]]
    } 

  import plp.external.implementation.{ toFunction, fromFunction }

  override private[plp] def binding[Z, Y]: C[Z] ?=> (Z ?=> C[Y]) => C[Y] =
    `z?=>cy` =>
      val `z=>cy`: Z => C[Y] = toFunction(`z?=>cy`)
      bindingD(`z=>cy`(summon[Z]))
