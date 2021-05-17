package plp.external.implementation.active.writing

import plp.external.specification.writing.Writable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.Active

import plp.external.implementation.active.`=>A`

import plp.external.implementation.active.writing.`=>AW`

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.{
  given Computation[Active]
}

import plp.external.implementation.active.{
  given Materialization[`=>A`, Unit, Unit]
}

import plp.internal.implementation.computation.transformation.writing.givenWritingTransformedMaterialization

given [W: Writable]: Materialization[`=>AW`[W], Unit, (W, Unit)] =
  givenWritingTransformedMaterialization[W, Active, Unit, Unit]