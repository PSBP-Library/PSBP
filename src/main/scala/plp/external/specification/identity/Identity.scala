package plp.external.specification.identity

trait Identity[>-->[- _, + _]]:

  // declared

  def id[Z]: Z >--> Z

  // defined

  def `z>-->z`[Z]: Z >--> Z =
    id

  def `y>-->y`[Y]: Y >--> Y =
    id

  def `x>-->x`[X]: X >--> X =
    id      
  
  def `w>-->w`[W]: W >--> W =
    id

  def `v>-->v`[V]: V >--> V =
    id 

  // ...

  def `u>-->u`: Unit >--> Unit =
    id

  // ... 

