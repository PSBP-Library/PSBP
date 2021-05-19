package examples.implementation.stdOut.writing.bigInt.reading

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.reading.Reading

import plp.external.specification.writing.Writable

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.specification.writing.Writing

import plp.external.specification.materialization.Materialization

import plp.external.implementation.stdOut.StdOut

import examples.specification.program.factorial

import plp.external.implementation.stdOut.{
  given Writable[StdOut]
}

import plp.external.implementation.stdOut.{
  given Writing[StdOut, ?]
}

def materializedMainFactorial[
  >-->[- _, + _]: Program
                : [>-->[- _, + _]] =>> Reading[BigInt, >-->]
                : [>-->[- _, + _]] =>> ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->]
                : [>-->[- _, + _]] =>> Materialization[>-->, Unit, BigInt ?=> (StdOut, Unit)]
]: Unit ?=> (BigInt ?=> (StdOut, Unit)) =

  val materialization: Materialization[>-->, Unit, BigInt ?=> (StdOut, Unit)] =
    summon[Materialization[>-->, Unit, BigInt ?=> (StdOut, Unit)]]
  import materialization.materialize 

  import plp.external.specification.program.main.toMain

  materialize(toMain(factorial))