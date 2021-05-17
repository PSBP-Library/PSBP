// package plp.external.implementation.active.reading

// import plp.external.specification.materialization.Materialization

// import plp.external.implementation.active.Active

// import plp.external.implementation.active.reading.`=>AR`

// // givens

// import plp.external.implementation.active.givens.activeComputation

// import plp.external.implementation.active.givens.activeMaterialization

// import plp.internal.implementation.computation.transformation.reading.givens.readingTransformedMaterialization

// given activeReadingMaterialization[R]: Materialization[`=>AR`[R], Unit, R ?=> Unit] =
//   readingTransformedMaterialization[R, Active, Unit, Unit]