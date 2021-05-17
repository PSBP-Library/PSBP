package plp.internal.specification.computation

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.binding.Binding

private[plp] trait Computation[C[+ _]] 
  extends Resulting[C] 
  with Binding[C]