package psbp.internal.implementation.computation.transformation.writing

import psbp.external.specification.types.&&

private[psbp] type WritingTransformed[
  W
  , C[+ _]
] = [Y] =>> C[W && Y] 