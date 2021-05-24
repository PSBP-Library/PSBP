package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.types.&&

import plp.external.specification.writing.Writable

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.contextNaturalTransformation.?~>

import plp.internal.specification.transformation.Transformation

private[plp] given givenWritingTransformedComputation[
  W : Writable
  , D[+ _]: Computation
]: Transformation[
  D
  , WritingTransformed[W, D]
] with Computation[WritingTransformed[W, D]] with 

  private type C[+Y] = WritingTransformed[W, D][Y]

  private type `?=>C` = [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    // cresult => cresultD
    result => resultD
    , binding => bindingD
    , lift => liftD
  }

  private val writable = summon[Writable[W]]
  import writable.{
    nothing
    , append
  }  

  import plp.external.implementation.fromFunction

  // override private[plp] val `d?~>c`: D ?~> C = new {
  //   def apply[Z]: D[Z] ?=> C[Z] =
  //     bindingD {
  //       given (W && Z) = (nothing, summon[Z])
  //       resultD
  //     }
  // } 

  import plp.external.implementation.toFunction

  override private[plp] val `d?~>c`: D ?~> C = new {
    def apply[Z]: D[Z] ?=> C[Z] =
      bindingD(resultD((nothing, summon[Z]))) 
  } 


  // private def bindD[Z, Y]: (Z => D[Y]) => (D[Z] => D[Y]) =
  //   `z=>dy` =>
  //     toFunction(bindingD(fromFunction(`z=>dy`)) )
  //     // dz => 
  //     //   given D[Z] = dz
  //     //   bindingD(fromFunction(`z=>dy`)) 
          
  // override private[plp] def binding[Z, Y]: C[Z] ?=> (Z ?=> C[Y]) => C[Y] =
  //   `z?=>cy` =>
  //     bindD{
  //       val `(w&&z)=>cy`: (W && Z) => C[Y] = { (w1, z) =>
  //         val `(w&&y)=>cy`: (W && Y) => C[Y] = { (w2, y) =>
  //           toFunction(resultD)(append(w1, w2), y)
  //         }
  //         bindD(`(w&&y)=>cy`)(toFunction(`z?=>cy`)(z))
  //       }
  //       `(w&&z)=>cy`
  //     }(summon[D[(W && Z)]])

  // override private[plp] def binding[Z, Y]: C[Z] ?=> (Z ?=> C[Y]) => C[Y] =
  //   `z?=>cy` =>
  //     liftD[W && Z, W && Y]{
  //       val `(w&&z)=>cy`: (W && Z) => C[Y] = { (w1, z) =>
  //         val `(w&&y)=>cy`: (W && Y) => C[Y] = { (w2, y) =>
  //           toFunction(resultD)(append(w1, w2), y)
  //         }
  //         val `d[w&&y]` = toFunction(`z?=>cy`)(z)
  //         liftD[W && Y, W && Y](`(w&&y)=>cy`)(`d[w&&y]`)
  //       }
  //       `(w&&z)=>cy`
  //     }(summon[D[(W && Z)]]) 
  
  override private[plp] def binding[Z, Y]: C[Z] ?=> (Z ?=> C[Y]) => C[Y] =
    `z?=>cy` =>
      liftD[W && Z, W && Y]{
        (w1, z) =>
          liftD[W && Y, W && Y]{ (w2, y) =>
            resultD(append(w1, w2), y)
          }(toFunction(`z?=>cy`)(z))
      }(summon[D[(W && Z)]])   



  // private def bindD[Z, Y](dz: D[Z]): (Z => D[Y]) => D[Y] =
  //    `z=>dy` =>
  //       given D[Z] = dz
  //       bindingD(fromFunction(`z=>dy`)) 
          
  // override private[plp] def binding[Z, Y]: C[Z] ?=> (Z ?=> C[Y]) => C[Y] =
  //   `z?=>cy` =>
  //     bindD(summon[D[(W, Z)]])((w1, z) =>
  //       bindD(toFunction(`z?=>cy`)(z))((w2, y) =>
  //         given (W && Y) = (append(w1, w2), y)
  //         resultD
  //       )
  //     )   

  // override private[plp] def bind[Z, Y](
  //   cz: C[Z]
  //   , `z=>cy`: => Z => C[Y]
  // ): C[Y] =
  //   bindD(cz, (w1, z) =>
  //     bindD(`z=>cy`(z), (w2, y) =>
  //       given (W && Y) = (append(w1, w2), y)
  //       resultD
  //     )
  //   )  

  // override private[plp] val `d?~>c`: D ?~> C = new {
  //   def apply[Z]: D[Z] ?=> C[Z] =
  //     bindD(
  //       summon[D[Z]]
  //       , z => 
  //           given (W && Z) = (nothing, z)
  //           resultD
  //     )
  // }  

  // override private[plp] def bind[Z, Y](
  //   cz: C[Z]
  //   , `z=>cy`: => Z => C[Y]
  // ): C[Y] =
  //   bindD(cz, (w1, z) =>
  //     bindD(`z=>cy`(z), (w2, y) =>
  //       given (W && Y) = (append(w1, w2), y)
  //       resultD
  //     )
  //   )



  // override private[plp] def bind[Z, Y](
  //   cz: C[Z]
  //   , `z=>cy`: => Z => C[Y]
  // ): C[Y] =
  //   bindD(cz, (w1, z) =>
  //     val (w2, y): W && Y = `z=>cy`(z)
  //     given (W && Y) = (append(w1, w2), y)
  //     resultD
  //   )
