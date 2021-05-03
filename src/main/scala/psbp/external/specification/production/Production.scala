package psbp.external.specification.production

trait Production[>-->[- _, + _], +Z]:

  // declared

  def produce: Unit >--> Z

  // defined

  def `u>-->z`: Unit >--> Z =
    produce