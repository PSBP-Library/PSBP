package examples.implementation.stdOut.writing.givens

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.writing.ConvertibleToWritable

import psbp.external.implementation.stdOut.StdOut

// givens

import psbp.external.implementation.stdOut.givens.argumentAndResultConvertibleToStdOutWritable

given ((BigInt && BigInt) => String) = 
  (i, j) => 
    s"applying factorial to argument $i yields result $j"

given factorialArgumentAndResultConvertibleToStdOutWritable[
  >-->[- _, + _]: Program 
]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
  argumentAndResultConvertibleToStdOutWritable
