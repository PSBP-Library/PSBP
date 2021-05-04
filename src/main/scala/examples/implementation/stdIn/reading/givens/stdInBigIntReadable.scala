package examples.implementation.stdIn.reading.givens

import psbp.external.specification.reading.Readable

import psbp.external.implementation.stdIn.StdIn

given stdInBigIntReadable: Readable[StdIn[BigInt]] with

  override val r: StdIn[BigInt] = 

    println("Please type an integer")
  
    StdIn {
      _ => 
        BigInt(scala.io.StdIn.readInt)
    } 