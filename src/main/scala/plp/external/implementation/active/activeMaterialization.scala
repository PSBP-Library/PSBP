package plp.external.implementation.active

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.`=>A`

given Materialization[`=>A`, Unit, Unit] with
  val materialize = identity // : (Unit `=>A` Unit) => Unit ?=> Unit =
    identity
    // `u>-->u` => 
    //   `u>-->u` // (summon[Unit])