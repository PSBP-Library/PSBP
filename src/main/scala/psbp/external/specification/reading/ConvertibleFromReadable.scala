package psbp.external.specification.reading

trait ConvertibleFromReadable[R: Readable, +Z, >-->[- _, + _]]:

  // declared

  def convert: R >--> Z

  // defined

  def `r>-->z`: R >--> Z =
    convert