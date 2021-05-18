package plp.internal.specification.computation.transformation

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.naturalTransformation.~>
  
private[plp] trait ComputationTransformation[
  D[+ _]: Resulting 
  , C[+ _]
] extends Resulting[C]:

  // declared

  private[plp] val `d~>c`: D ~> C

  // defined

  override private[plp] def `i~>c`: Id ~> C = 

    val resulting = 
      summon[Resulting[D]]
    import resulting.{
      `i~>c` => `i~>d`
    }

    `i~>d` ~> `d~>c`

  // override private[plp] def `i~>c`: Id ~> T = 

  //   `i~>t` // .apply
  
  // override private[plp] def result[Z]: Z => T[Z] = 

  //   val resulting = summon[Resulting[F]]
  //   import resulting.{
  //     `i~>c` => `i~>f`
  //   }

  //   (`i~>f` ~> `f~>t`).apply

// package plp.internal.specification.computation.transformation

// import plp.internal.specification.resulting.Resulting

// import plp.internal.specification.naturalTransformation.~>
  
// private[plp] trait ComputationTransformation[
//   F[+ _]: Resulting 
//   , T[+ _]
// ] extends Resulting[T]:

//   // declared

//   private[plp] val `f~>t`: F ~> T

//   // defined
  
//   override private[plp] def result[Z]: Z => T[Z] = 

//     import `f~>t`.{ 
//       apply => `fz=>tz` 
//     }

//     val resulting = summon[Resulting[F]]
//     import resulting.{ 
//       result => `z=>fz` 
//     }
    
//     `z=>fz` andThen `fz=>tz` 