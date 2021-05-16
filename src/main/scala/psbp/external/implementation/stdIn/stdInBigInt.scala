package psbp.external.implementation.stdIn

val stdInBigInt: StdIn[BigInt] =
  StdIn(effect = { _ => BigInt(scala.io.StdIn.readInt) })

