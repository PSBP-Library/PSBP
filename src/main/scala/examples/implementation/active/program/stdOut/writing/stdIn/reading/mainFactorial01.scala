package examples.implementation.active.program.stdOut.writing.stdIn.reading

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

// givens

import psbp.external.specification.writing.Writable

import psbp.external.implementation.stdOut.StdOut

import psbp.external.implementation.stdOut.givens.{ given Writable[StdOut] }

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingProgram

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingReading

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingWriting

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingMaterialization


def writeTo01(stdOut: => StdOut) =
  stdOut.effect(())
  
@main def mainFactorial01(args: String*): Unit =

  import examples.implementation.unit.givens.pleaseTypeAnInteger

  import examples.implementation.stdIn.reading.givens.stdInBigIntArgument

  lazy val (stdOut, _) = materializedMainFactorial

  writeTo01(stdOut)

  

