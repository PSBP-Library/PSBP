package examples.implementation.active.program.stdOut.writing.stdIn.reading.factorial

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.reading.Reading

import plp.external.specification.writing.Writable

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.stdOut.StdOut

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

import plp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
  , given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, Unit)]
}

import examples.implementation.pleaseTypeAnInteger.{
  given Unit
}

import examples.implementation.stdIn.{
  given BigInt
}

import examples.implementation.stdOut.writing.factorial.{
  given ConvertibleToWritable[(BigInt && BigInt), StdOut, ?]
}

import plp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}

@main def mainFactorial(args: String*): Unit =

  materializedMainFactorial
    


