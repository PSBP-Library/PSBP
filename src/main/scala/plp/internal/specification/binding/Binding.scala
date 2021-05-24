package plp.internal.specification.binding

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.joining.Joining

import plp.internal.specification.contextNaturalTransformation.?~>

private[plp] trait Binding[C[+ _]]
  extends Joining[C]:

  // declared

  // private[plp] def bind[Z, Y] (
  //   cz: C[Z]
  //   , `z=>cy`: => Z => C[Y]
  // ): C[Y]

  // defined

  import plp.external.implementation.{ toFunction, fromFunction }

  private[plp] def lift[Z, Y]: (Z => C[Y]) => (C[Z] => C[Y]) =
    `z=>dy` =>
      toFunction(binding(fromFunction(`z=>dy`)))      

  private[plp] def binding[Z, Y]: C[Z] ?=> (Z ?=> C[Y]) => C[Y]

  // extension [Z, Y] (cz: C[Z]) 
  //   private[plp] def >=(`z=>cy`: => Z => C[Y]): C[Y] =
  //     given C[Z] = cz
  //     binding(`z=>cy`(summon[Z]))
  
  // extension [Z, Y] (cz: C[Z]) 
  //   private[plp] def >=(`z=>cy`: => Z => C[Y]): C[Y] =
  //     given C[Z] = cz
  //     binding(fromFunction(`z=>cy`))

  extension [Z, Y] (cz: C[Z]) 
    private[plp] def >=(`z=>cy`: => Z => C[Y]): C[Y] =
      lift(`z=>cy`)(cz)
      // toFunction(binding(fromFunction(`z=>cy`)))(cz)      

  // extension [Z, Y] (cz: C[Z]) 
  //   private[plp] def >=?(`z?=>cy`: => Z ?=> C[Y]): C[Y] =
  //     toFunction(binding(`z?=>cy`))(cz) 

  override private[plp] def `cc?~>c`: CC ?~> C =
    new {
      override private[plp] def apply[Z]: CC[Z] ?=> C[Z] =
        summon[CC[Z]] >= 
          identity
    }


   

