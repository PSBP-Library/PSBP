package psbp.internal.specification.computation

import psbp.internal.specification.resulting.Resulting

import psbp.internal.specification.binding.Binding

private[psbp] trait Computation[C[+ _]] 
  extends Resulting[C] 
  with Binding[C]