package examples.implementation.active.program.stdOut.writing.stdIn.reading

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

// givens

import psbp.external.implementation.stdOut.givens.stdOutWritable

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingProgram

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingReading

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingWriting

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingMaterialization

import psbp.external.implementation.stdOut.StdOut

def writeTo02(stdOut: => StdOut) =
  import psbp.external.implementation.stdOut.givens.stdOutWriting
  stdOut

@main def mainFactorial02(args: String*): Unit =

  import examples.implementation.unit.givens.pleaseTypeAnInteger

  import examples.implementation.stdIn.reading.givens.stdInBigIntArgument

  lazy val (stdOut, _) = materializedMainFactorial
  
  writeTo02(stdOut)
  


