package plp.internal.specification.naturalTransformation

private[plp] trait ~>[-F[+ _], +T[+ _]]:

  // declared

  private[plp] def apply[Z]: F[Z] => T[Z]

  
