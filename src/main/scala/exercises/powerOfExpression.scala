package exercises

import plp.external.specification.types.{
  &&
  , ||
}  

import plp.external.specification.function.{ 
  `z>-->(z&&z)`
  , `(z&&y)>-->z` 
  , `(z&&y&&x)>-->(y&&x)` 
  , `(z||z)>-->z`
  , `(y||x)>-->b`
  , `(y||x)>-->y`
  , `(y||x)>-->x`
}

import plp.external.specification.program.Program

def `construct using &&&`[
  Z, Y, X
  , >-->[- _, + _]: Program
](
  `z>-->y`: Z >--> Y
  , `z>-->x`: => Z >--> X
): Z >--> (Y && X) =
  `z>-->(z&&z)` >--> (`z>-->y` &&& `z>-->x`)

def `construct using Let`[
  Z, Y, X
  , >-->[- _, + _]: Program
](
  `z>-->y`: Z >--> Y
  , `z>-->x`: => Z >--> X
): Z >--> (Y && X) =
 
  val program: Program[>-->] = 
    summon[Program[>-->]]
  import program.Let

  Let {
    `z>-->y`
  } In {
    Let {
      `(z&&y)>-->z` >--> `z>-->x`
    } In {
      `(z&&y&&x)>-->(y&&x)`
    }
  }

def `conditionally using |||`[
  Z, Y, X
  , >-->[- _, + _]: Program
](
  `y>-->z`: => Y >--> Z
  , `x>-->z`: => X >--> Z
): (Y || X) >--> Z =
  (`y>-->z` ||| `x>-->z`) >--> `(z||z)>-->z`

def `conditionally using If`[
  Z, Y, X
  ,  >-->[- _, + _]: Program
](
  `y>-->z`: => Y >--> Z
  , `x>-->z`: => X >--> Z
): (Y || X) >--> Z =

  val program: Program[>-->] = 
    summon[Program[>-->]]
  import program.If

  If(`(y||x)>-->b`) {
    `(y||x)>-->y` >--> `y>-->z`
  } Else {
    `(y||x)>-->x` >--> `x>-->z`
  }
