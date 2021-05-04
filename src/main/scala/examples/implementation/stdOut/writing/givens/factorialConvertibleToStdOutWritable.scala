package examples.implementation.stdOut.writing.givens

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.writing.ConvertibleToWritable

import psbp.external.implementation.stdOut.StdOut

// givens

import psbp.external.implementation.stdOut.givens.convertibleToStdOutWritable

given String = "factorial"

given factorialConvertibleToStdOutWritable[
  >-->[- _, + _]: Program 
]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
  convertibleToStdOutWritable
