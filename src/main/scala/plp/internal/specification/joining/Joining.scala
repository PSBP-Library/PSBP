package plp.internal.specification.joining

import plp.internal.specification.contextNaturalTransformation.?~>

private[plp] trait Joining[C[+ _]]:
        
  private[plp] type CC[+Z] = C[C[Z]]

  private[plp] def `cc?~>c`: CC ?~> C

  private[plp] def cJoin[Z]: CC[Z] ?=> C[Z] =
    `cc?~>c`.apply

  import plp.external.implementation.toFunction
  
  private[plp] def join[Z]: CC[Z] => C[Z] =
    toFunction(cJoin)
