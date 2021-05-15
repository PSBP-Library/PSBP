package examples.implementation.stdIn.givens

import psbp.external.implementation.stdIn.StdIn

given stdInBigInt: StdIn[BigInt] =   
  StdIn {
    _ => 
      BigInt(scala.io.StdIn.readInt)
  } 
