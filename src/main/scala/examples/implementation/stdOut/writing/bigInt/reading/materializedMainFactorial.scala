package examples.implementation.stdOut.writing.bigInt.reading

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.program.main.toMain

import plp.external.specification.production.Production

import plp.external.specification.consumption.Consumption

import plp.external.specification.reading.Reading

import plp.external.specification.writing.Writable

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.specification.writing.Writing

import plp.external.specification.materialization.Materialization

import plp.external.implementation.stdOut.StdOut

import examples.specification.program.factorial

import plp.external.specification.reading.{
  given Production[?, ?]
}

import plp.external.specification.program.writing.{
  given Consumption[?, ?]
}

import plp.external.implementation.stdOut.{
  given Writable[StdOut]
}

import examples.implementation.stdOut.writing.factorial.{
  given ConvertibleToWritable[(BigInt && BigInt), StdOut, ?]
}

def materializedMainFactorial[
  >-->[- _, + _]: Program
                : [>-->[- _, + _]] =>> Reading[BigInt, >-->]
                : [>-->[- _, + _]] =>> Writing[StdOut, >-->]
                : [>-->[- _, + _]] =>> Materialization[>-->, Unit, BigInt ?=> (StdOut, Unit)]
]: Unit ?=> (BigInt ?=> (StdOut, Unit)) =

  val materialization: Materialization[>-->, Unit, BigInt ?=> (StdOut, Unit)] =
    summon[Materialization[>-->, Unit, BigInt ?=> (StdOut, Unit)]]
  import materialization.materialize 

  materialize(toMain(factorial))
