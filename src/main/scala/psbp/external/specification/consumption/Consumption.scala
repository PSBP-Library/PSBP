package psbp.external.specification.consumption

trait Consumption[-Y, >-->[- _, + _]]:

  // declared

  def consume: Y >--> Unit

  // defined

  def `y>-->u`: Y >--> Unit =
    consume