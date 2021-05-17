package plp.external.implementation.active

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.`=>A`

given Materialization[`=>A`, Unit, Unit] with
  val materialize: (Unit `=>A` Unit) => Unit ?=> Unit =
    `u>-->u` => 
      `u>-->u`(summon[Unit])