package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.types.&&

import plp.external.specification.writing.Writable

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.naturalTransformation.~>

import plp.internal.specification.computation.transformation.ComputationTransformation

private[plp] given givenWritingTransformedComputation[
  W : Writable
  , D[+ _]: Computation
]: ComputationTransformation[
  D
  , WritingTransformed[W, D]
] with Computation[WritingTransformed[W, D]] with 

  private type C[+Y] = WritingTransformed[W, D][Y]

  private type `=>C` = [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    `i~>c` => `i~>d`
    , bind => bindD
  }

  private val writable = summon[Writable[W]]
  import writable.{
    nothing
    , append
  }  

  override private[plp] val `d~>c`: D ~> C = new {
    def apply[Z]: D[Z] => C[Z] =
      dz =>
        bindD(dz, z => `i~>d`((nothing, z)))
  }  

  override private[plp] def bind[Z, Y](
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y] =
    bindD(cz, (w1, z) =>
      val (w2, y): W && Y = `z=>cy`(z)
      `i~>d`(append(w1, w2), y)
    )
