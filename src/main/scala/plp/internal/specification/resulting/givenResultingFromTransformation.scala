package plp.internal.specification.resulting

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.naturalTransformation.~>

import plp.internal.specification.transformation.Transformation

given givenResultingFromTransformation[
  D[+ _]: Resulting 
  , C[+ _]: [C[+ _]] =>> Transformation[D, C]
]: Resulting[C] with

  private val transformation: Transformation[D, C] = summon[Transformation[D, C]]
  import transformation.`d~>c`

  // defined

  override private[plp] def `i~>c`: I ~> C = 

    val resulting = 
      summon[Resulting[D]]
    import resulting.{
      `i~>c` => `i~>d`
    }

    `i~>d` ~> `d~>c`