package plp.external.specification.program

import scala.language.postfixOps

import plp.external.specification.types.{ 
  &&
  , || 
}

import plp.external.specification.classification.Classification

import plp.external.specification.function.Function

import plp.external.specification.construction.Construction

import plp.external.specification.condition.Condition

import plp.external.specification.function.{ 
  `(z&&y)>-->z`
  , `(z&&y)>-->y`
  , `z>-->(z||y)`
  , `y>-->(z||y)`
  , `(z&&b)>-->(z||z)` 
}

trait Program[>-->[- _, + _]]
  extends Classification[>-->]
  , Function[>-->]
  , Construction[>-->]
  , Condition[>-->]:

  private given function: Function[>-->] = this

  // defined

  extension [Z, Y, X, W] (`z>-->x`: Z >--> X) 
    def &&&(`y>-->w`: => Y >--> W): (Z && Y) >--> (X && W) =
      (`(z&&y)>-->z` >--> `z>-->x`) && (`(z&&y)>-->y` >--> `y>-->w`)  

  extension [Z, Y, X, W] (`x>-->z`: => X >--> Z) 
    def |||(`w>-->y`: => W >--> Y): (X || W) >--> (Z || Y) =
      (`x>-->z` >--> `z>-->(z||y)`) || (`w>-->y` >--> `y>-->(z||y)`)

  def Let[Z, Y, X](`z>-->y`: Z >--> Y): In[Z, Y, X] =
    new {
      def In(`(z&&y)>-->x`: => (Z && Y) >--> X): Z >--> X =
        (`z>-->z` && `z>-->y`) >--> `(z&&y)>-->x`
    } 

  trait In[Z, Y, X]:
    def In(`(z&&y)>-->x`: => (Z && Y) >--> X): Z >--> X
    
  def If[Z, Y](`z>-->b`: Z >--> Boolean): Apply[Z, Y] =
    new {
      override def apply(`z>-t->y`: => Z >--> Y): Else[Z, Y] =
        new {
          override def Else(`z>-f->y`: => Z >--> Y): Z >--> Y =
            Let {
              `z>-->b`
            } In {
              `(z&&b)>-->(z||z)`
            } >--> {
              `z>-t->y` || `z>-f->y`
            }  
        }
    } 

  trait Apply[Z, Y]:
    def apply(`z>-t->y`: => Z >--> Y): Else[Z, Y]

  trait Else[Z, Y]:
    def Else(`z>-f->y`: => Z >--> Y): Z >--> Y


