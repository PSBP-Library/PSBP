package psbp.external.specification.program.writing

import psbp.external.specification.consumption.Consumption

import psbp.external.specification.program.Program

import psbp.external.specification.writing.{
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

  override def consume: Y >--> Unit =
    `y>-->w` >--> `w>-->u`