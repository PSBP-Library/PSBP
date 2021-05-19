package examples.implementation.stdOut.writing.factorial

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.implementation.stdOut.StdOut

import plp.external.implementation.stdOut.givenConvertibleToStdOut

given [
  >-->[- _, + _]: Program 
]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
  givenConvertibleToStdOut
