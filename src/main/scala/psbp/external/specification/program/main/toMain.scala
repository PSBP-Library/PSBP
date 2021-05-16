package psbp.external.specification.program.main

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.production.Production

import psbp.external.specification.consumption.Consumption

def toMain[
  Z, Y
  , >-->[- _, + _]
    : [>-->[- _, + _]] =>> Production[>-->, Z]
    : Program
    : [>-->[- _, + _]] =>> Consumption[Z && Y, >-->]
](`z>-->y`: Z >--> Y) =

  val production = summon[Production[>-->, Z]]
  import production.{
    produce => `u>-->z`
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