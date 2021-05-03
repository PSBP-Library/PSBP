package psbp.external.specification.condition

import psbp.external.specification.types.||

trait Condition[>-->[- _, + _]]:

  // declared

  def conditionally[Z, Y, X] (
    `y>-->z`: => Y >--> Z
    , `x>-->z`: => X >--> Z
  ): (Y || X) >--> Z

  // defined
  
  extension [Z, Y, X] (`y>-->z`: => Y >--> Z) 
    def ||(`x>-->z`: => X >--> Z): (Y || X) >--> Z =
      conditionally(`y>-->z`, `x>-->z`)