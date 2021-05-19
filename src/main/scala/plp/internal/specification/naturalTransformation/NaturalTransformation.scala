package plp.internal.specification.naturalTransformation

private[plp] trait ~>[-F[+ _], +G[+ _]]:

  // declared

  private[plp] def apply[Z]: F[Z] => G[Z]

extension [F[+ _], G[+ _], H[+ _]] (`f~>g`: F ~> G) 
  private[plp] def ~>(`g~>h`: => G ~> H): F ~> H =
    new {
      override private[plp] def apply[Z]: F[Z] => H[Z] =
        `f~>g`.apply andThen `g~>h`.apply
    }

  
