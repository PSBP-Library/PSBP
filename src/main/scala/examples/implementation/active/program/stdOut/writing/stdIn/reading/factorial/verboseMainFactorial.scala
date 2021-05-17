package examples.implementation.active.program.stdOut.writing.stdIn.reading.factorial

import plp.external.specification.program.Program

import plp.external.specification.reading.Reading

import plp.external.specification.writing.Writable

import plp.external.specification.writing.Writing

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.stdOut.StdOut

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

import plp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}

import plp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
  , given Writing[StdOut, `=>AWR`[StdOut, BigInt]]
  , given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, Unit)]
}
  
@main def verboseMainFactorial(args: String*): Unit =

  given Unit = println("Please type an integer")

  import plp.external.implementation.stdIn.stdInBigInt.effect

  given BigInt = effect(())

  val (stdOut, _) = materializedMainFactorial

  stdOut.effect(())
  

