package psbp.external.implementation.active.writing

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.`=>A`

import psbp.external.implementation.active.writing.`=>AW`

import psbp.internal.specification.computation.Computation

import psbp.external.implementation.active.{
  given Computation[Active]
}

import psbp.external.implementation.active.{
  given Materialization[`=>A`, Unit, Unit]
}

import psbp.internal.implementation.computation.transformation.writing.writingTransformedMaterialization

given activeWritingMaterialization[W: Writable]: Materialization[`=>AW`[W], Unit, (W, Unit)] =
  writingTransformedMaterialization[W, Active, Unit, Unit]