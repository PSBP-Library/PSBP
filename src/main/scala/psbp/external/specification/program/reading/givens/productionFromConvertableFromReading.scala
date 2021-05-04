package psbp.external.specification.program.reading.givens

import psbp.external.specification.production.Production

import psbp.external.specification.reading.{
  Readable
  , ConvertibleFromReadable
  , Reading
}

import psbp.external.specification.program.reading.ProgramWithReading

given productionFromConvertibleFromReading [
  Z: [Z] =>> ConvertibleFromReadable[R, Z, >-->]
  , R: Readable
  , >-->[- _, + _]: [>-->[- _, + _]] =>> ProgramWithReading[R, >-->]
]: Production[>-->, Z] with

  val convertibleFromReadable = summon[ConvertibleFromReadable[R, Z, >-->]]
  import convertibleFromReadable.`r>-->z`
    
  val reading = summon[Reading[R, >-->]]
  import reading.`u>-->r`
  
  override def produce: Unit >--> Z =
    `u>-->r` >--> `r>-->z`