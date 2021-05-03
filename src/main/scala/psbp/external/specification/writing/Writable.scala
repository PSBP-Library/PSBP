package psbp.external.specification.writing

import psbp.external.specification.types.&&

trait Writable[W]:

  // declared

  def empty: W

  def append: (W && W) => W
  
  // defined 

  extension (w1: W) 
    def +(w2: W): W =
      append(w1, w2)
