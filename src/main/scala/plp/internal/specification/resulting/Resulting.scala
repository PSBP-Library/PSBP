package plp.internal.specification.resulting

import plp.internal.specification.naturalTransformation.~>

private[plp] trait Resulting[C[+ _]]:

  type Id[+Z] = Z

  private[plp] def `i~>c`: Id ~> C
