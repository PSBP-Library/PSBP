package plp.external.specification.program.main

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.reading.Reading

import plp.external.specification.consumption.Consumption

def toMain[
  Z, Y
  , >-->[- _, + _]
    : [>-->[- _, + _]] =>> Reading[Z, >-->]
    : Program
    : [>-->[- _, + _]] =>> Consumption[Z && Y, >-->]
](`z>-->y`: Z >--> Y) =

  val reading = summon[Reading[Z, >-->]]
  import reading.{
    read => `u>-->z`
  }

  val program = summon[Program[>-->]]
  import program.Let    

  val consumption = summon[Consumption[Z && Y, >-->]]
  import consumption.{
    consume => `(z&&y)>-->u`
  }

  `u>-->z`
    >--> {
      Let { 
        `z>-->y`
      } In { 
        `(z&&y)>-->u`
      }
    }

// package plp.external.specification.program.main

// import plp.external.specification.types.&&

// import plp.external.specification.program.Program

// import plp.external.specification.production.Production

// import plp.external.specification.consumption.Consumption

// def toMain[
//   Z, Y
//   , >-->[- _, + _]
//     : [>-->[- _, + _]] =>> Production[>-->, Z]
//     : Program
//     : [>-->[- _, + _]] =>> Consumption[Z && Y, >-->]
// ](`z>-->y`: Z >--> Y) =

//   val production = summon[Production[>-->, Z]]
//   import production.{
//     produce => `u>-->z`
//   }

//   val program = summon[Program[>-->]]
//   import program.Let    

//   val consumption = summon[Consumption[Z && Y, >-->]]
//   import consumption.{
//     consume => `(z&&y)>-->u`
//   }

//   `u>-->z`
//     >--> {
//       Let { 
//         `z>-->y`
//       } In { 
//         `(z&&y)>-->u`
//       }
//     }