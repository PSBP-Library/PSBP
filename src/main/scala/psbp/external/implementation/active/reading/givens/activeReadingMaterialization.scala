package psbp.external.implementation.active.reading.givens

import psbp.external.specification.reading.Readable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.reading.`=>AR`

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.external.implementation.active.givens.activeMaterialization

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedMaterialization

given activeReadingMaterialization[R: Readable]: Materialization[`=>AR`[R], Unit, R ?=> Unit] =
  readingTransformedMaterialization[R, Active, Unit, Unit]