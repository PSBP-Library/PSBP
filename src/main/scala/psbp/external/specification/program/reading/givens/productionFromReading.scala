package psbp.external.specification.program.reading.givens

import psbp.external.specification.production.Production

import psbp.external.specification.reading.Reading

given productionFromReading [
  Z: [Z] =>> Reading[Z, >-->]
  , >-->[- _, + _]  
]: Production[>-->, Z] with
    
  val reading = summon[Reading[Z, >-->]]
  import reading.read
  
  override def produce: Unit >--> Z =
    read