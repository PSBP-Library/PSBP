package plp.exercises

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.naturalTransformation.~>

private[plp] def resultAsNaturalTransformation[C[+ _]: Resulting]: ([Z] =>> Z) ~> C =
  val resulting: Resulting[C] = summon[Resulting[C]]
  import resulting.result

  new {
    override def apply[Z]: Z => C[Z] =
      result
  }