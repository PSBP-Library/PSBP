package psbp.external.implementation.stdOut

import psbp.external.specification.types.&&

import psbp.external.specification.writing.Writable

// import psbp.external.implementation.stdOut.StdOut

given stdOutWritable: Writable[StdOut] with

  // defined

  override def nothing: StdOut = 
    StdOut(effect = identity )

  override def append: (StdOut && StdOut) => StdOut =
    case (StdOut(firstEffect), StdOut(secondEffect)) =>
      StdOut(effect = firstEffect andThen secondEffect)
