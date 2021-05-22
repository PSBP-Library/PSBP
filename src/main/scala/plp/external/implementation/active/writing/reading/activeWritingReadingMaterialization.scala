package plp.external.implementation.active.writing.reading

import plp.external.specification.writing.Writable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.ActiveWriting

import plp.external.implementation.active.writing.`=>AW`

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
}  

import plp.external.implementation.active.writing.{
  given Materialization[`=>AW`[?], Unit, (?, Unit)]
}

given [
  W: Writable
  , R
]: Materialization[
  `=>AWR`[W, R]
  , Unit
  , R ?=> (W, Unit)
] with 

  override val materialize = 
    identity


