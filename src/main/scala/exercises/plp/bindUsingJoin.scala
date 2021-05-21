package plp.exercises

import plp.internal.specification.joining.Joining

private[plp] trait FunctionLifting[C[+ _]]:

  private[plp] def lift[Z, Y]: (Z => Y) => (C[Z] => C[Y])

private[plp] def bindUsingJoin[
  Z, Y
  , C[+ _]: Joining
          : FunctionLifting
] (
  cz: C[Z]
  , `z=>cy`: => Z => C[Y]
): C[Y] =
  
  val joining: Joining[C] = summon[Joining[C]]
  import joining.join

  val lifting: FunctionLifting[C] = summon[FunctionLifting[C]]
  import lifting.lift

  given C[C[Y]] = lift(`z=>cy`)(cz)

  join.apply

  // given joining.CC[Z] = lift(`z=>cy`)(cz)

  // join.apply(lift(`z=>cy`)(cz))

  ???