package introduction

extension [Z, Y] (parameter: Z) 
  def bind(function: Z => Y): Y =
    function apply parameter