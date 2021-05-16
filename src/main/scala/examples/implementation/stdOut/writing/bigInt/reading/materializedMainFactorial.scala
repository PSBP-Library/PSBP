package examples.implementation.stdOut.writing.bigInt.reading

import psbp.external.specification.program.Program

import psbp.external.specification.program.main.toMain

import psbp.external.specification.reading.Reading

import psbp.external.specification.writing.Writing

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.stdOut.StdOut

import examples.specification.program.factorial

// givens

import psbp.external.specification.program.reading.givens.productionFromReading

import psbp.external.specification.program.writing.givens.consumptionFromConvertibleToWritable

import psbp.external.implementation.stdOut.givens.stdOutWritable

import examples.implementation.stdOut.writing.givens.factorialArgumentAndResultConvertibleToStdOutWritable

def materializedMainFactorial[
  >-->[- _, + _]: Program
                : [>-->[- _, + _]] =>> Reading[BigInt, >-->]
                : [>-->[- _, + _]] =>> Writing[StdOut, >-->]
                : [>-->[- _, + _]] =>> Materialization[>-->, Unit, BigInt ?=> (StdOut, (StdOut, Unit))]
]: Unit ?=> (BigInt ?=> (StdOut, (StdOut, Unit))) =

  val materialization: Materialization[>-->, Unit, BigInt ?=> (StdOut, (StdOut, Unit))] =
    summon[Materialization[>-->, Unit, BigInt ?=> (StdOut, (StdOut, Unit))]]
  import materialization.materialize 

  materialize(toMain(factorial))
