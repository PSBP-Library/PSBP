// package plp.external.specification.production

// trait Production[>-->[- _, + _], +Z]:

//   // declared

//   private[plp] def produce: Unit >--> Z

//   // defined

//   private[plp] def `u>-->z`: Unit >--> Z =
//     produce