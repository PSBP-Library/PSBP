package examples.implementation.stdOut.writing.factorial

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.writing.ConvertibleToWritable

import psbp.external.implementation.stdOut.StdOut

import psbp.external.implementation.stdOut.AsMessage

import psbp.external.implementation.stdOut.convertibleToStdOut

given AsMessage[BigInt && BigInt] with 
  override val message: (BigInt && BigInt) => String =
    (i, j) => 
      s"applying factorial to argument $i yields result $j" 

given [
  >-->[- _, + _]: Program 
]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
  convertibleToStdOut
