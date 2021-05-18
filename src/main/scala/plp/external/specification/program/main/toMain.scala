package plp.external.specification.program.main

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.reading.Reading

import plp.external.specification.writing.{
  Writable
  , ConvertibleToWritable
  ,  Writing
}

def toMain[
  Z, Y
  , W: Writable
  , >-->[- _, + _]
    : [>-->[- _, + _]] =>> Reading[Z, >-->]
    : Program
    : [>-->[- _, + _]] =>> ConvertibleToWritable[(Z && Y), W, >-->]
    : [>-->[- _, + _]] =>> Writing[W, >-->]
](`z>-->y`: Z >--> Y) =

  val reading = summon[Reading[Z, >-->]]
  import reading.{
    read => `u>-->z`
  }

  val program = summon[Program[>-->]]
  import program.Let    

  val convertibleToWritable = summon[ConvertibleToWritable[(Z && Y), W, >-->]]
  import convertibleToWritable.{
    convert => `z&&y>-->w`
  }

  val writing = summon[Writing[W, >-->]]
  import writing.`w>-->u`

  val `z&&y>-->u`: (Z && Y) >--> Unit = 
    `z&&y>-->w` >--> `w>-->u`

  `u>-->z`
    >--> {
      Let { 
        `z>-->y`
      } In { 
        `z&&y>-->u`
      }
    }
