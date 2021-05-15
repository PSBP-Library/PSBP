package psbp.external.specification.writing

trait ConvertibleToWritable[
  -Y
  , W: Writable, 
  >-->[- _, + _]
]:

  // declared

  def convert: Y >--> W

  // defined
  
  def `y>-->w`: Y >--> W =
    convert
