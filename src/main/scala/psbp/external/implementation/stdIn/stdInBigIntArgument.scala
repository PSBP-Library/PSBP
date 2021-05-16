package psbp.external.implementation.stdIn

val stdInBigIntArgument: StdIn[BigInt] =
  StdIn(effect = { _ => BigInt(scala.io.StdIn.readInt) })

