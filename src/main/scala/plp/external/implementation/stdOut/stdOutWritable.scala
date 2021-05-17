package plp.external.implementation.stdOut

import plp.external.specification.types.&&

import plp.external.specification.writing.Writable

given Writable[StdOut] with

  // defined

  override private[plp] def nothing: StdOut = 
    StdOut(effect = identity )

  override private[plp] def append: (StdOut && StdOut) => StdOut =
    case (StdOut(firstEffect), StdOut(secondEffect)) =>
      StdOut(effect = firstEffect andThen secondEffect)
