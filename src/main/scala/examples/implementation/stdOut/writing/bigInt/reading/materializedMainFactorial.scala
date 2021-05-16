package examples.implementation.stdOut.writing.bigInt.reading

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.program.main.toMain

import psbp.external.specification.production.Production

import psbp.external.specification.consumption.Consumption

import psbp.external.specification.reading.Reading

import psbp.external.specification.writing.Writable

import psbp.external.specification.writing.ConvertibleToWritable

import psbp.external.specification.writing.Writing

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.stdOut.StdOut

import examples.specification.program.factorial

import psbp.external.specification.program.reading.{
  given Production[?, ?]
}

import psbp.external.specification.program.writing.{
  given Consumption[?, ?]
}

import psbp.external.implementation.stdOut.{
  given Writable[StdOut]
}

import examples.implementation.stdOut.writing.factorial.{
  given ConvertibleToWritable[(BigInt && BigInt), StdOut, ?]
}

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
