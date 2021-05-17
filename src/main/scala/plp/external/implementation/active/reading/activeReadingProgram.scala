// package plp.external.implementation.active.reading

// import plp.external.specification.program.Program

// import plp.external.implementation.active.Active

// import plp.external.implementation.active.reading.{
//   ActiveReading
//   , `=>AR`
// }

// import plp.internal.specification.computation.Computation


// import plp.external.implementation.computation.programFromComputation

// import plp.external.implementation.active.givens.activeComputation

// import plp.internal.implementation.computation.transformation.reading.givens.readingTransformedComputation

// private[plp] given activeReadingComputation[R]: Computation[ActiveReading[R]] = 
//   readingTransformedComputation[R, Active]

// given activeReadingProgram[R]: Program[`=>AR`[R]] =
//   programFromComputation[ActiveReading[R]]