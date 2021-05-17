package plp.internal.specification.binding

private[plp] trait Binding[C[+ _]]:

  // declared

  private[plp] def bind[Z, Y] (
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y]

  // defined

  private[plp] def join[Z] (ccz: C[C[Z]]): C[Z] =
    bind(ccz, identity)

  extension [Z, Y] (cz: C[Z]) 
    private[plp] def >=(`z=>cy`: => Z => C[Y]): C[Y] =
      bind(cz, `z=>cy`)

   

