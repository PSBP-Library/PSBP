package examples.implementation.active.program.stdOut.writing.stdIn.reading.factorial

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.writing.ToString

import plp.external.specification.reading.Reading

import plp.external.specification.writing.Writable

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.stdOut.StdOut

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

import plp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
  , given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, Unit)]
}

import plp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}
  
@main def verboseMainFactorial(args: String*): Unit =

  given Unit = println("Please type an integer")

  import plp.external.implementation.stdIn.stdInBigInt

  given BigInt = stdInBigInt.effect(())

  import plp.external.implementation.stdOut.givenConvertibleToStdOut

  given ToString[BigInt && BigInt] with 
    override val _toString: (BigInt && BigInt) => String =
      (i, j) => 
        s"applying factorial to argument $i yields result $j" 

  given [
    >-->[- _, + _]: Program 
  ]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
    givenConvertibleToStdOut

  val (stdOut, _) = materializedMainFactorial

  stdOut.effect(())
  

