package psbp.external.implementation.stdOut

trait AsMessage[Z]:
  val message: Z => String