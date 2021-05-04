package examples.implementation.stdOut.writing.stdIn.reading.givens

import psbp.external.specification.program.Program

import psbp.external.specification.program.toMain

import psbp.external.specification.materialization.Materialization

import examples.specification.program.factorial

// givens

import psbp.external.specification.program.reading.givens.productionFromConvertibleFromReadable

import psbp.external.specification.program.writing.givens.consumptionFromConvertibleToWritable

import psbp.external.specification.program.reading.givens.programWithReading

import psbp.external.implementation.stdIn.givens.convertibleFromStdInReadable

import psbp.external.implementation.stdOut.givens.stdOutProgramWithWriting

import psbp.external.implementation.stdOut.givens.stdOutWritable

import examples.implementation.stdIn.reading.givens.stdInBigIntReadable

import examples.implementation.stdOut.writing.givens.factorialConvertibleToStdOutWritable

def materializedMainFactorial[
  Z, Y
  , >-->[- _, + _]: Program 
                  : [>-->[- _, + _]] =>> Materialization[>-->, Z, Y]
]: Z ?=> Y =
  
  val materialization: Materialization[>-->, Z, Y] = 
    summon[Materialization[>-->, Z, Y]]
  import materialization.materialize

  materialize(toMain(factorial))
