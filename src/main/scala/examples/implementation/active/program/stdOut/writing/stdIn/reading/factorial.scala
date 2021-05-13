package examples.implementation.active.program.stdOut.writing.stdIn.reading

@main def factorial(args: String*): Unit =

  // givens

  import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingProgram

  import psbp.external.implementation.stdOut.StdOut

  import psbp.external.implementation.stdIn.StdIn

  import psbp.external.implementation.active.writing.reading.`=>AWR`

  import examples.implementation.stdIn.reading.givens.stdInBigInt

  import examples.implementation.stdIn.reading.givens.stdInBigIntReadable

  import psbp.external.implementation.stdOut.givens.stdOutWritable

  import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingMaterialization

  import examples.implementation.stdOut.writing.stdIn.reading.materializedMainFactorial

  import examples.implementation.givens.unit

  val foo: (StdOut, (StdOut, Unit)) = 
    materializedMainFactorial[Unit, StdIn[BigInt] ?=> (StdOut, (StdOut, Unit)),`=>AWR`[StdOut, StdIn[BigInt]] ]

  // // givens

  // import psbp.external.implementation.active.givens.activeProgram

  // import psbp.external.implementation.active.givens.activeMaterialization

  // import examples.implementation.stdOut.writing.stdIn.reading.materializedMainFactorial

  // import examples.implementation.givens.unit    

  // val foo: Unit = materializedMainFactorial