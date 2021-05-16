package psbp.external.implementation.active

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.`=>A`

given activeMaterialization: Materialization[`=>A`, Unit, Unit] with
  val materialize: (Unit `=>A` Unit) => Unit ?=> Unit =
    `u>-->u` => 
      `u>-->u`(summon[Unit])