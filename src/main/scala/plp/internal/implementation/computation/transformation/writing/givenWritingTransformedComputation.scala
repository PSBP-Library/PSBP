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
    result => resultD
    , bind => bindD
  }

  private val writable = summon[Writable[W]]
  import writable.{
    nothing
    , append
  }  

  override private[plp] val `d?~>c`: D ?~> C = new {
    def apply[Z]: D[Z] ?=> C[Z] =
      bindD(
        summon[D[Z]]
        , z => 
            given (W && Z) = (nothing, z)
            resultD
      )
  }  

  override private[plp] def bind[Z, Y](
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y] =
    bindD(cz, (w1, z) =>
      bindD(`z=>cy`(z), (w2, y) =>
        given (W && Y) = (append(w1, w2), y)
        resultD
      )
    )

  // override private[plp] def bind[Z, Y](
  //   cz: C[Z]
  //   , `z=>cy`: => Z => C[Y]
  // ): C[Y] =
  //   bindD(cz, (w1, z) =>
  //     val (w2, y): W && Y = `z=>cy`(z)
  //     given (W && Y) = (append(w1, w2), y)
  //     resultD
  //   )
