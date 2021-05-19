package plp.internal.specification.resulting

import plp.internal.specification.naturalTransformation.~>

private[plp] trait Resulting[C[+ _]]:

  private[plp] type I[+Z] = Z

  private[plp] def `i~>c`: I ~> C
