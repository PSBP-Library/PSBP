package plp.exercises

import plp.internal.specification.binding.Binding

private[plp] trait Lifting[C[+ _]]:

  private[plp] def lift[Z, Y]: (Z => Y) => (C[Z] => C[Y])

private[plp] def bindUsingJoinAsNaturalTransformation[
  Z, Y
  , C[+ _]: Binding
          : Lifting
] (
  cz: C[Z]
  , `z=>cy`: => Z => C[Y]
): C[Y] =
  
  val lifting: Lifting[C] = summon[Lifting[C]]
  import lifting.lift

  joinAsNaturalTransformation apply lift(`z=>cy`)(cz)