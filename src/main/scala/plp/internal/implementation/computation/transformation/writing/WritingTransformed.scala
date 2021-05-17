package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.types.&&

private[plp] type WritingTransformed[
  W
  , C[+ _]
] = [Y] =>> C[W && Y] 