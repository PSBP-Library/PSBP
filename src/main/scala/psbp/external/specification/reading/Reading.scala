package psbp.external.specification.reading

trait Reading[R: Readable, >-->[- _, + _]]:

  // declared

  def read: Unit >--> R

  // defined

  def `u>-->r`: Unit >--> R =
    read
