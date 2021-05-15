package examples.implementation.active.program.stdOut.writing.stdIn.reading

import examples.implementation.stdOut.writing.stdIn.reading.materializedMainFactorial

// givens

import psbp.external.implementation.stdOut.givens.stdOutWritable

// no effect needed

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingProgram

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingReading

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingWriting

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingMaterialization

@main def factorial(args: String*): Unit =

  import examples.implementation.unit.givens.pleaseTypeAnInteger

  import examples.implementation.bigInt.givens.bigInt

  // materializedMainFactorial

  val (stdOut, (_, _)) = materializedMainFactorial

  val effect = stdOut.`u=>u`
  
  effect(())

  // val (_, (stdOut, _)) = materializedMainFactorial

  // val effect = stdOut.`u=>u`
  
  // effect(())  

