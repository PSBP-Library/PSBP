package examples.implementation.bigInt.givens

given bigInt: BigInt =
  BigInt(scala.io.StdIn.readInt)