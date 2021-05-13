package psbp.external.specification.program.writing.givens

import psbp.external.specification.consumption.Consumption

import psbp.external.specification.program.Program

import psbp.external.specification.writing.{
  Writable
  , ConvertibleToWritable
  , Writing
}

// import psbp.external.specification.program.writing.ProgramWithWriting

given consumptionFromConvertibleToWritable [
  Y: [Y] =>> ConvertibleToWritable[Y, W, >-->]
  , W: Writable
     : [W] =>> Writing[W, >-->]
  , >-->[- _, + _]: Program 
  // , >-->[- _, + _]: [>-->[- _, + _]] =>> ProgramWithWriting[W, >-->]
]: Consumption[Y, >-->] with 

  private val convertibleToWritable = summon[ConvertibleToWritable[Y, W, >-->]]
  import convertibleToWritable.`y>-->w`
  
  private val writing = summon[Writing[W, >-->]]
  import writing.`w>-->u`

  override def consume: Y >--> Unit =
    `y>-->w` >--> `w>-->u`