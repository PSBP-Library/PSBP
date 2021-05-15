// package psbp.external.implementation.active.reading.givens

// import psbp.external.specification.program.Program

// import psbp.external.implementation.active.Active

// import psbp.external.implementation.active.reading.{
//   ActiveReading
//   , `=>AR`
// }

// import psbp.internal.specification.computation.Computation

// // givens

// import psbp.external.implementation.computation.givens.programFromComputation

// import psbp.external.implementation.active.givens.activeComputation

// import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedComputation

// private[psbp] given activeReadingComputation[R]: Computation[ActiveReading[R]] = 
//   readingTransformedComputation[R, Active]

// given activeReadingProgram[R]: Program[`=>AR`[R]] =
//   programFromComputation[ActiveReading[R]]