package psbp.external.implementation.active.writing.givens

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.`=>AW`

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.external.implementation.active.givens.activeMaterialization

import psbp.internal.implementation.computation.transformation.writing.givens.writingTransformedMaterialization

given activeWritingMaterialization[W: Writable]: Materialization[`=>AW`[W], Unit, (W, Unit)] =
  writingTransformedMaterialization[W, Active, Unit, Unit]