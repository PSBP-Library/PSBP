package examples.implementation.stdIn.reading.givens

import scala.language.postfixOps

import psbp.external.specification.program.Program

import psbp.external.specification.reading.Reading

import examples.implementation.bigInt.givens.bigInt

given stdInBigIntReading[
  >-->[- _, + _]: Program 
]: Reading[BigInt, >-->] with

  object function {
    val read: Unit => BigInt =
      _ => 
        bigInt
  }

  override def read: Unit >--> BigInt =
    function.read asProgram


    