package psbp.external.implementation.active.givens

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.`=>A`

given activeMaterialization: Materialization[`=>A`, Unit, Unit] with
  val materialize: (Unit `=>A` Unit) => Unit ?=> Unit =
    `u>-->u` => 
      val u = summon[Unit]
      `u>-->u`(u)