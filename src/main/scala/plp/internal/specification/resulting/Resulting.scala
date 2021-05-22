package plp.internal.specification.resulting

import plp.internal.specification.contextNaturalTransformation.?~>

private[plp] trait Resulting[C[+ _]]:

  private[plp] type I[+Z] = Z

  private[plp] def `i?~>c`: I ?~> C

  private[plp] def result[Z]: I[Z] ?=> C[Z] =
    `i?~>c`.apply
