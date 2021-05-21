package plp.internal.specification.joining

import plp.internal.specification.naturalTransformation.~>

private[plp] trait Joining[C[+ _]]:
        
  private[plp] type CC[+Z] = C[C[Z]]

  private[plp] def join[Z]: CC ~> C