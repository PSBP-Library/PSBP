package psbp.external.specification.function

import scala.language.postfixOps

import psbp.external.specification.types.{ 
  &&
  , ||
}

// construction

def `(z&&y)=>z`[Z, Y]: (Z && Y) => Z =
  (z, _) => 
    z

def `(z&&y)=>y`[Z, Y]: (Z && Y) => Y =
  (_, y) => 
    y

// condition

import ||.{ 
  Left
  , Right
}

def `z=>(z||y)`[Z, Y]: Z => (Z || Y) =
  z =>
    Left(z)

def `y=>(z||y)`[Z, Y]: Y => (Z || Y) =
  y =>
    Right(y)

def `(z&&b)=>(z||z)`[Z]: (Z && Boolean) => (Z || Z) = {
  case (z, true) => 
    Left(z)
  case (z, false) => 
    Right(z) 
}

def foldSum[Z, Y, X]: ((Y => Z) && (X => Z)) => (Y || X) => Z =
  (`z=>x`, `y=>x`) => 
    case Left(z) => `z=>x`(z)
    case Right(y) =>`y=>x`(y)  

// function

// construction

def `(z&&y)>-->z`[
  Z, Y
  , >-->[- _, + _]: Function
]: (Z && Y) >--> Z =
  `(z&&y)=>z` asProgram
    
def `(z&&y)>-->y`[
  Z, Y
  , >-->[- _, + _]: Function
]: (Z && Y) >--> Y =
  `(z&&y)=>y` asProgram

// condition

def `z>-->(z||y)`[
  Z, Y
  , >-->[- _, + _]: Function
]: Z >--> (Z || Y) =
  `z=>(z||y)` asProgram
  
def `y>-->(z||y)`[
  Z, Y
  , >-->[- _, + _]: Function
]: Y >--> (Z || Y) =
  `y=>(z||y)` asProgram 

def `(z&&b)>-->(z||z)`[
  Z
  , >-->[- _, + _]: Function
]: (Z && Boolean) >--> (Z || Z) =
  `(z&&b)=>(z||z)` asProgram