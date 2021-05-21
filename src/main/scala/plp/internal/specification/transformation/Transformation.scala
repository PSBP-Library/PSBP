package plp.internal.specification.transformation

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.contextNaturalTransformation.?~>
  
private[plp] trait Transformation[
  D[+ _]: Resulting 
  , C[+ _]
] extends Resulting[C]:

  // declared

  private[plp] val `d?~>c`: D ?~> C

  // defined

  override private[plp] def `i?~>c`: I ?~> C = 

    val resulting = 
      summon[Resulting[D]]
    import resulting.{
      `i?~>c` => `i?~>d`
    }

    `i?~>d` ?~> `d?~>c`
