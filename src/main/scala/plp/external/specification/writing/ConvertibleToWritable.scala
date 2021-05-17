package plp.external.specification.writing

trait ConvertibleToWritable[
  -Y
  , W: Writable, 
  >-->[- _, + _]
]:

  // declared

  private[plp] def convert: Y >--> W

  // defined
  
  private[plp] def `y>-->w`: Y >--> W =
    convert
