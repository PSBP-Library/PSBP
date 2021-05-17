package plp.external.specification.writing

import plp.external.specification.types.&&

trait Writable[W]:

  // declared

  private[plp] def nothing: W

  private[plp] def append: (W && W) => W
  
  // defined 

  extension (w1: W) 
    private[plp] def +(w2: W): W =
      append(w1, w2)
