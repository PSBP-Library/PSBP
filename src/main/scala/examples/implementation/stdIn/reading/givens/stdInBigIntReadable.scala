package examples.implementation.stdIn.reading.givens

import psbp.external.specification.reading.Readable

import psbp.external.implementation.stdIn.StdIn

import examples.implementation.stdIn.givens.stdInBigInt

given stdInBigIntReadable: Readable[StdIn[BigInt]] with

  override val r: StdIn[BigInt] =

    stdInBigInt  


    