package plp.external.implementation.active

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.`=>A`

given Materialization[`=>A`, Unit, Unit] with
  override val materialize = 
    identity // [Unit ?=> Unit]