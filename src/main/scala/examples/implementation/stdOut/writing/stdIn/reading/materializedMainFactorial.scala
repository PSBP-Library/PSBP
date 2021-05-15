package examples.implementation.stdOut.writing.stdIn.reading

import psbp.external.specification.program.Program

import psbp.external.specification.program.toMain

import psbp.external.specification.materialization.Materialization

import examples.specification.program.factorial

// givens

import psbp.external.specification.program.reading.givens.productionFromReading

import psbp.external.specification.program.writing.givens.consumptionFromConvertibleToWritable

import psbp.external.implementation.stdOut.StdOut

import psbp.external.implementation.stdOut.givens.stdOutWritable

import psbp.external.implementation.stdOut.givens.stdOutWriting

import examples.implementation.stdIn.reading.givens.stdInBigIntReading

import examples.implementation.stdOut.writing.givens.factorialConvertibleToStdOutWritable

import psbp.external.specification.reading.Reading

import psbp.external.specification.writing.Writing

import psbp.external.specification.materialization.Materialization

def materializedMainFactorial[
  >-->[- _, + _]: Program
                : [>-->[- _, + _]] =>> Reading[BigInt, >-->]
                : [>-->[- _, + _]] =>> Writing[StdOut, >-->]
                : [>-->[- _, + _]] =>> Materialization[>-->, Unit, BigInt ?=> (StdOut, (StdOut, Unit))]
]: Unit ?=> (BigInt ?=> (StdOut, (StdOut, Unit))) =

  val materialization: Materialization[>-->, Unit, BigInt ?=> (StdOut, (StdOut, Unit))] =
    summon[Materialization[>-->, Unit, BigInt ?=> (StdOut, (StdOut, Unit))]]

  materialization.materialize(toMain(factorial))
