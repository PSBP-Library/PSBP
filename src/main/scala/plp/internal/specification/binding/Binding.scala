package plp.internal.specification.binding

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.joining.Joining

import plp.internal.specification.contextNaturalTransformation.?~>

private[plp] trait Binding[C[+ _]]
  extends Joining[C]:

  // declared

  private[plp] def bind[Z, Y] (
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y]

  // defined

  extension [Z, Y] (cz: C[Z]) 
    private[plp] def >=(`z=>cy`: => Z => C[Y]): C[Y] =
      bind(cz, `z=>cy`)
        
  private[plp] def join[Z]: CC ?~> C =
    new {
      override private[plp] def apply[Z]: CC[Z] ?=> C[Z] =
        summon[CC[Z]] >= 
          identity
    }


   

