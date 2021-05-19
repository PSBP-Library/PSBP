package examples.implementation.stdOut.writing.factorial

import plp.external.specification.types.&&

import plp.external.specification.writing.ToString

given ToString[BigInt && BigInt] with 
  override val _toString: (BigInt && BigInt) => String =
    (i, j) => 
      s"applying factorial to argument $i yields result $j" 