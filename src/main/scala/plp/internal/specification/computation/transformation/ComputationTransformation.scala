package plp.internal.specification.computation.transformation

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.naturalTransformation.~>
  
private[plp] trait ComputationTransformation[
  F[+ _]: Resulting 
  , T[+ _]
] extends Resulting[T]:

  // declared

  private[plp] val `f~>t`: F ~> T

  // defined
  
  override private[plp] def result[Z]: Z => T[Z] = 

    import `f~>t`.{ 
      apply => `fz=>tz` 
    }

    val resulting = summon[Resulting[F]]
    import resulting.{ 
      result => `z=>fz` 
    }
    
    `z=>fz` andThen `fz=>tz` 