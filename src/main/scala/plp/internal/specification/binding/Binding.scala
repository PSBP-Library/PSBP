package plp.internal.specification.binding

import plp.internal.specification.naturalTransformation.~>

private[plp] trait Binding[C[+ _]]:

  // declared

  private[plp] def bind[Z, Y] (
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y]

  // defined

  extension [Z, Y] (cz: C[Z]) 
    private[plp] def >=(`z=>cy`: => Z => C[Y]): C[Y] =
      bind(cz, `z=>cy`)
        
  type CC[+Z] = C[C[Z]]

  private[plp] def join[Z]: CC ~> C =
    new {
      override private[plp] def apply[Z]: CC[Z] => C[Z] =
       ccz =>
         bind(ccz, identity)   
    }



   

