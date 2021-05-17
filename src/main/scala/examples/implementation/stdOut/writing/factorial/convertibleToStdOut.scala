package examples.implementation.stdOut.writing.factorial

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.implementation.stdOut.StdOut

import plp.external.implementation.stdOut.ToMessage

import plp.external.implementation.stdOut.givenConvertibleToStdOut

given ToMessage[BigInt && BigInt] with 
  override val message: (BigInt && BigInt) => String =
    (i, j) => 
      s"applying factorial to argument $i yields result $j" 

given [
  >-->[- _, + _]: Program 
]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
  givenConvertibleToStdOut
