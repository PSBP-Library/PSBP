package plp.external.implementation.stdOut

trait ToMessage[Z]:
  val message: Z => String