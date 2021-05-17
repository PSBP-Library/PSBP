package examples.specification.function

import scala.language.postfixOps

import plp.external.specification.types.&&

import plp.external.specification.function.Function

object function {

  val isZero: BigInt => Boolean =
    n =>
      n == 0
  
  def one[Z]: Z => BigInt =
    _ =>
      1
  
  val subtractOne: BigInt => BigInt =
    n =>
      n - 1
    
  val multiply: BigInt && BigInt => BigInt =
    (n, m) =>
      n * m
    
}

def isZero[
  >-->[- _, + _]: Function
]: BigInt >--> Boolean = 
  function.isZero asProgram
  
def one[
  Z
  , >-->[- _, + _]: Function
]: Z >--> BigInt =
  function.one asProgram

def subtractOne[
  >-->[- _, + _]: Function
]: BigInt >--> BigInt =
  function.subtractOne asProgram

def multiply[
  >-->[- _, + _]: Function
]: (BigInt && BigInt) >--> BigInt =  
  function.multiply asProgram






