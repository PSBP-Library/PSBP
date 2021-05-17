package plp.internal.specification.resulting

private[plp] trait Resulting[C[+ _]]:

  // declared

  private[plp] def result[Z]: Z => C[Z]
