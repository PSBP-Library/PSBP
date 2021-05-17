package plp.external.specification.consumption

trait Consumption[-Y, >-->[- _, + _]]:

  // declared

  private[plp] def consume: Y >--> Unit

  // defined

  private[plp] def `y>-->u`: Y >--> Unit =
    consume