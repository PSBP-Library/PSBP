package psbp.external.specification.reading

trait Reading[R, >-->[- _, + _]]:

  // declared

  def read: Unit >--> R

  // defined

  def `u>-->r`: Unit >--> R =
    read
