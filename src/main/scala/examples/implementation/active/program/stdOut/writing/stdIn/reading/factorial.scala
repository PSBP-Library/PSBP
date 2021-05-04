package examples.implementation.active.program.stdOut.writing.stdIn.reading

// givens

import psbp.external.implementation.active.givens.activeProgram

import psbp.external.implementation.active.writing.givens.activeWritingProgram

import psbp.external.implementation.active.givens.activeMaterialization

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingProgram

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingMaterialization

import psbp.external.implementation.stdOut.givens.stdOutWritable

import examples.implementation.givens.unit

import examples.implementation.stdOut.writing.stdIn.reading.givens.materializedMainFactorial


@main def factorial(args: String*): Unit =

  import psbp.external.implementation.stdOut.StdOut

  val foo: (StdOut, (StdOut, Unit)) = materializedMainFactorial


/*

package examples.implementation.active.programWithReadingAndWriting.reading.stdIn.writing.stdOut

import psbp.external.implementation.stdOut.givenStdOutWritable

import examples.implementation.givenUnit

import psbp.external.implementation.readingWithWritingActive.givenReadingWithWritingActiveProgram

import psbp.external.implementation.readingWithWritingActive.givenReadingWithWritingActiveMaterialization

@main def factorial(args: String*): Unit =

  materializedMainFactorial
  
  // val (stdOut1, (stdOut2, u)) = materializedMainFactorial

  // val `u=>u`: Unit => Unit = stdOut2.`u=>u`

  // `u=>u` apply ()

*/