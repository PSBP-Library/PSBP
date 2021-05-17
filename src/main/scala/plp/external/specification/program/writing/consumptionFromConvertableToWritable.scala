package plp.external.specification.program.writing

import plp.external.specification.consumption.Consumption

import plp.external.specification.program.Program

import plp.external.specification.writing.{
  Writable
  , ConvertibleToWritable
  , Writing
}

given [
  Y: [Y] =>> ConvertibleToWritable[Y, W, >-->]
  , W: Writable
     : [W] =>> Writing[W, >-->]
  , >-->[- _, + _]: Program 
]: Consumption[Y, >-->] with 

  private val convertibleToWritable = summon[ConvertibleToWritable[Y, W, >-->]]
  import convertibleToWritable.`y>-->w`
  
  private val writing = summon[Writing[W, >-->]]
  import writing.`w>-->u`

  override private[plp] def consume: Y >--> Unit =
    `y>-->w` >--> `w>-->u`