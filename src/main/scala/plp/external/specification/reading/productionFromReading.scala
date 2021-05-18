// package plp.external.specification.reading

// import plp.external.specification.production.Production

// import plp.external.specification.reading.Reading

// given [
//   Z: [Z] =>> Reading[Z, >-->]
//   , >-->[- _, + _]  
// ]: Production[>-->, Z] with
    
//   val reading = summon[Reading[Z, >-->]]
//   import reading.read
  
//   override private[plp] def produce: Unit >--> Z =
//     read