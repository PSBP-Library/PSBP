package plp.external.implementation.stdIn

case class StdIn[Z](effect: Unit => Z)
