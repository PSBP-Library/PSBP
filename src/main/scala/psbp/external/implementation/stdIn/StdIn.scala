package psbp.external.implementation.stdIn

case class StdIn[Z](effect: Unit => Z)
