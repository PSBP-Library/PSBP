package examples.implementation.active.program.stdOut.writing.stdIn.reading.factorial

import psbp.external.specification.program.Program

import psbp.external.specification.reading.Reading

import psbp.external.specification.writing.Writable

import psbp.external.specification.writing.Writing

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.writing.reading.`=>AWR`

import psbp.external.implementation.stdOut.StdOut

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

import psbp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}

import psbp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
  , given Writing[StdOut, `=>AWR`[StdOut, BigInt]]
  , given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, (StdOut, Unit))]
}
  
@main def mainFactorial01(args: String*): Unit =

  import examples.implementation.pleaseTypeAnInteger.{
    given Unit
  }

  import examples.implementation.stdIn.reading.{
    given BigInt
  }

  val (stdOut, _) = materializedMainFactorial

  stdOut.effect(())
  

