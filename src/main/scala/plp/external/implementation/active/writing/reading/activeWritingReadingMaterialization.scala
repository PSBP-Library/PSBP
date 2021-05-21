package plp.external.implementation.active.writing.reading

import plp.external.specification.writing.Writable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.ActiveWriting

import plp.external.implementation.active.writing.`=>AW`

import plp.external.implementation.active.writing.reading.`=>AWR`

// import plp.external.implementation.active.writing.reading.ActiveWritingReading

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
}  

import plp.external.implementation.active.writing.{
  given Materialization[`=>AW`[?], Unit, (?, Unit)]
}

// import plp.internal.implementation.computation.transformation.reading.givenReadingTransformedMaterialization

given [
  W: Writable
  , R
]: Materialization[
  `=>AWR`[W, R]
  , Unit
  , R ?=> (W, Unit)
] with 

  // type `=>AWR[W,R]` = [Z, Y] =>> Z ?=> ActiveWritingReading[W, R][Y]
    
  override val materialize = // : (Unit `=>AWR[W,R]` Unit) => Unit ?=> R ?=> (W, Unit) =
    identity
    // (`u=>awru`: Unit ?=> (R ?=> (W, Unit))) => 
    //   `u=>awru`
      // val foo = givenReadingTransformedMaterialization.materialize(`u=>awru`)
      // ???   
    // `u=>awru` => 
    //   givenReadingTransformedMaterialization.materialize(`u=>awru`) match {
    //     case (w, (_, u)) => (w, u)
    //   }

