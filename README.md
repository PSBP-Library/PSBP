# `PSBP`

## `Identity`

```scala
package psbp.external.specification.identity

trait Identity[>-->[- _, + _]]:

  // declared

  def identity[Z]: Z >--> Z

  // defined

  def `z>-->z`[Z]: Z >--> Z =
    identity

  def `y>-->y`[Y]: Y >--> Y =
    identity

  def `x>-->x`[X]: X >--> X =
    identity      
  
  def `w>-->w`[W]: W >--> W =
    identity

  def `v>-->v`[V]: V >--> V =
    identity 

  // ...

  def `u>-->u`: Unit >--> Unit =
    identity

  // ... 
```

## `Composition`

```scala
package psbp.external.specification.composition

trait Composition[>-->[- _, + _]]:

  // declared

  private[psbp] def andThen[Z, Y, X](
    `z>-->y`: Z >--> Y
    , `y>-->x`: => Y >--> X
  ): Z >--> X

  // defined
  
  extension [Z, Y, X] (`z>-->y`: Z >--> Y) 
    def >-->(`y>-->x`: => Y >--> X): Z >--> X =
      andThen(`z>-->y`, `y>-->x`)
```

## `Classification`

```scala
package psbp.external.specification.classification

import psbp.external.specification.identity.Identity

import psbp.external.specification.composition.Composition

trait Classification[>-->[- _, + _]]
  extends Identity[>-->]
  , Composition[>-->]
```

## `Function`

```scala
package psbp.external.specification.function

trait Function[>-->[- _, + _]]: 

  // declared

  def toProgram[Z, Y]: (Z => Y) => (Z >--> Y)

  // defined

  extension [Z, Y] (`z=>y`: Z => Y) 
    def asProgram: Z >--> Y =
      toProgram(`z=>y`)
```

## `Construction`

```scala
package psbp.external.specification.construction

import psbp.external.specification.types.&&

trait Construction[>-->[- _, + _]]:

  // declared

  def construct[Z, Y, X](
    `z>-->y`: Z >--> Y
    , `z>-->x`: => Z >--> X
  ): Z >--> (Y && X) 

  // defined

  extension [Z, Y, X] (`z>-->y`: Z >--> Y) 
    def &&(`z>-->x`: => Z >--> X): Z >--> (Y && X) =
      construct(`z>-->y`, `z>-->x`)
```

## `Condition`

```scala
package psbp.external.specification.condition

import psbp.external.specification.types.||

trait Condition[>-->[- _, + _]]:

  // declared

  def conditionally[Z, Y, X] (
    `y>-->z`: => Y >--> Z
    , `x>-->z`: => X >--> Z
  ): (Y || X) >--> Z

  // defined
  
  extension [Z, Y, X] (`y>-->z`: => Y >--> Z) 
    def ||(`x>-->z`: => X >--> Z): (Y || X) >--> Z =
      conditionally(`y>-->z`, `x>-->z`)
```

## `Production`

```scala
package psbp.external.specification.production

trait Production[>-->[- _, + _], +Z]:

  // declared

  def produce: Unit >--> Z

  // defined

  def `u>-->z`: Unit >--> Z =
    produce
```

## `Consumption`

```scala
package psbp.external.specification.consumption

trait Consumption[-Y, >-->[- _, + _]]:

  // declared

  def consume: Y >--> Unit

  // defined

  def `y>-->u`: Y >--> Unit =
    consume
```

## `Program`

```scala
package psbp.external.specification.program

import scala.language.postfixOps

import psbp.external.specification.types.{ 
  &&
  , || 
}

import psbp.external.specification.classification.Classification

import psbp.external.specification.function.Function

import psbp.external.specification.construction.Construction

import psbp.external.specification.condition.Condition

import psbp.external.specification.function.{ 
  `(z&&y)>-->z`
  , `(z&&y)>-->y`
  , `z>-->(z||y)`
  , `y>-->(z||y)`
  , `(z&&b)>-->(z||z)` 
}

trait Program[>-->[- _, + _]]
  extends Classification[>-->]
  , Function[>-->]
  , Construction[>-->]
  , Condition[>-->]:

  private given function: Function[>-->] = this

  // defined

  extension [Z, Y, X, W] (`z>-->x`: Z >--> X) 
    def &&&(`y>-->w`: => Y >--> W): (Z && Y) >--> (X && W) =
      (`(z&&y)>-->z` >--> `z>-->x`) && (`(z&&y)>-->y` >--> `y>-->w`)  

  extension [Z, Y, X, W] (`x>-->z`: => X >--> Z) 
    def |||(`w>-->y`: => W >--> Y): (X || W) >--> (Z || Y) =
      (`x>-->z` >--> `z>-->(z||y)`) || (`w>-->y` >--> `y>-->(z||y)`)

  def Let[Z, Y, X](`z>-->y`: Z >--> Y): In[Z, Y, X] =
    new {
      def In(`(z&&y)>-->x`: => (Z && Y) >--> X): Z >--> X =
        (`z>-->z` && `z>-->y`) >--> `(z&&y)>-->x`
    } 

  trait In[Z, Y, X]:
    def In(`(z&&y)>-->x`: => (Z && Y) >--> X): Z >--> X
    
  def If[Z, Y](`z>-->b`: Z >--> Boolean): Apply[Z, Y] =
    new {
      override def apply(`z>-t->y`: => Z >--> Y): Else[Z, Y] =
        new {
          override def Else(`z>-f->y`: => Z >--> Y): Z >--> Y =
            Let {
              `z>-->b`
            } In {
              `(z&&b)>-->(z||z)`
            } >--> {
              `z>-t->y` || `z>-f->y`
            }  
        }
    } 

  trait Apply[Z, Y]:
    def apply(`z>-t->y`: => Z >--> Y): Else[Z, Y]

  trait Else[Z, Y]:
    def Else(`z>-f->y`: => Z >--> Y): Z >--> Y

def toMain[
  Z, Y
  , >-->[- _, + _]: [>-->[- _, + _]] =>> Production[>-->, Z]
                  : Program
                  : [>-->[- _, + _]] =>> Consumption[Z && Y, >-->]
](`z>-->y`: Z >--> Y) =

  val production: Production[>-->, Z] = 
    summon[Production[>-->, Z]]
  import production.{
    produce => `u>-->z`
  }

  val program: Program[>-->] = 
    summon[Program[>-->]]
  import program.Let    

  val consumption: Consumption[Z && Y, >-->] = 
    summon[Consumption[Z && Y, >-->]]
  import consumption.{
    consume => `(z&&y)>-->u`
  }

  `u>-->z`
    >--> {
      Let { 
        `z>-->y`
      } In { 
        `(z&&y)>-->u`
      }
    }    
```

where

```scala
package psbp.external.specification.function

import scala.language.postfixOps

import psbp.external.specification.types.{ 
  &&
  , ||
}

// function

// construction

def `(z&&y)=>z`[Z, Y]: (Z && Y) => Z =
  (z, _) => 
    z

def `(z&&y)=>y`[Z, Y]: (Z && Y) => Y =
  (_, y) => 
    y

// condition

import ||.{ 
  Left
  , Right
}

def `z=>(z||y)`[Z, Y]: Z => (Z || Y) =
  z =>
    Left(z)

def `y=>(z||y)`[Z, Y]: Y => (Z || Y) =
  y =>
    Right(y)

def `(z&&b)=>(z||z)`[Z]: (Z && Boolean) => (Z || Z) = {
  case (z, true) => 
    Left(z)
  case (z, false) => 
    Right(z) 
} 

// function

// construction

def `(z&&y)>-->z`[
  Z, Y
  , >-->[- _, + _]: Function
]: (Z && Y) >--> Z =
  `(z&&y)=>z` asProgram
    
def `(z&&y)>-->y`[
  Z, Y
  , >-->[- _, + _]: Function
]: (Z && Y) >--> Y =
  `(z&&y)=>y` asProgram

// condition

def `z>-->(z||y)`[
  Z, Y
  , >-->[- _, + _]: Function
]: Z >--> (Z || Y) =
  `z=>(z||y)` asProgram
  
def `y>-->(z||y)`[
  Z, Y
  , >-->[- _, + _]: Function
]: Y >--> (Z || Y) =
  `y=>(z||y)` asProgram 

def `(z&&b)>-->(z||z)`[
  Z
  , >-->[- _, + _]: Function
]: (Z && Boolean) >--> (Z || Z) =
  `(z&&b)=>(z||z)` asProgram
```

## `Materialization`

```scala
package psbp.external.specification.materialization

trait Materialization[>-->[- _, + _], -Z, +Y]:

  // declared

  val materialize: (Unit >--> Unit) => Z ?=> Y
```

## `Readable`

```scala
package psbp.external.specification.reading

trait Readable[R]:

  // declared

  val r: R
```

## `ConvertibleFromReadable`

```scala
trait ConvertibleFromReadable[R: Readable, +Z, >-->[- _, + _]]:

  // declared

  def convert: R >--> Z

  // defined

  def `r>-->z`: R >--> Z =
    convert
```

## `Reading`

```scala
trait Reading[R: Readable, >-->[- _, + _]]:

  // declared

  def read: Unit >--> R

  // defined

  def `u>-->r`: Unit >--> R =
    read
```

## `Writable`

```scala
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
```

## `ConvertibleToWritable`

```scala
package psbp.external.specification.writing

trait ConvertibleToWritable[-Y, W: Writable, >-->[- _, + _]]:

  // declared

  def convert: Y >--> W

  // defined
  
  def `y>-->w`: Y >--> W =
    convert
```

## `Writing`

```scala
package psbp.external.specification.writing

trait Writing[W: Writable, >-->[- _, + _]]:

  // declared
  
  def write: W >--> Unit

  // defined

  def `w>-->u`: W >--> Unit =
    write
```

## `ProgramWithReading`

```scala
package psbp.external.specification.program.reading

import psbp.external.specification.program.Program

import psbp.external.specification.reading.{
  Readable
  , Reading
}

trait ProgramWithReading[R: Readable, >-->[- _, + _]] 
  extends Program[>-->] 
  , Reading[R, >-->]
```

## `ProgramWithWriting`

```scala
import psbp.external.specification.program.Program

import psbp.external.specification.writing.{
  Writable
  , Writing
}

trait ProgramWithWriting[W: Writable, >-->[- _, + _]] 
  extends Program[>-->] 
  , Writing[W, >-->]
```

## `programWithReading`

```scala
package psbp.external.specification.program.reading.givens

import psbp.external.specification.program.Program

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.specification.program.reading.ProgramWithReading

given programWithReading[
  R: Readable, 
  >-->[- _, + _]: Program
                : [>-->[- _, + _]] =>> Reading[R, >-->]
]: ProgramWithReading[R, >-->] with
 
  private val program: Program[>-->] = 
    summon[Program[>-->]]

  export program.identity
  export program.andThen

  export program.toProgram
  export program.construct
  export program.conditionally

  private val reading: Reading[R, >-->] = 
    summon[Reading[R, >-->]]

  export reading.read
```

## `productionFromConvertibleFromReading`

```scala
package psbp.external.specification.program.reading.givens

import psbp.external.specification.production.Production

import psbp.external.specification.reading.{
  Readable
  , ConvertibleFromReadable
  , Reading
}

import psbp.external.specification.program.reading.ProgramWithReading

given productionFromConvertibleFromReading [
  Z: [Z] =>> ConvertibleFromReadable[R, Z, >-->]
  , R: Readable
  , >-->[- _, + _]: [>-->[- _, + _]] =>> ProgramWithReading[R, >-->]
]: Production[>-->, Z] with

  val convertibleFromReadable: ConvertibleFromReadable[R, Z, >-->] = 
    summon[ConvertibleFromReadable[R, Z, >-->]]
  import convertibleFromReadable.`r>-->z`
    
  val reading: Reading[R, >-->] = 
    summon[Reading[R, >-->]]
  import reading.`u>-->r`
  
  override def produce: Unit >--> Z =
    `u>-->r` >--> `r>-->z`
```

## `programWithWriting`

```scala
package psbp.external.specification.program.writing.givens

import psbp.external.specification.program.Program

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.specification.program.writing.ProgramWithWriting

given programWithWriting[
  W: Writable, 
  >-->[- _, + _]: Program
                : [>-->[- _, + _]] =>> Writing[W, >-->]
]: ProgramWithWriting[W, >-->] with
 
  private val program: Program[>-->] = 
    summon[Program[>-->]]

  export program.identity
  export program.andThen

  export program.toProgram
  export program.construct
  export program.conditionally

  private val writing: Writing[W, >-->] = 
    summon[Writing[W, >-->]]

  export writing.write
```

## `consumptionFromConvertibleToWritable`

```scala
package psbp.external.specification.program.writing.givens

import psbp.external.specification.consumption.Consumption

import psbp.external.specification.writing.{
  Writable
  , ConvertibleToWritable
  , Writing
}

import psbp.external.specification.program.writing.ProgramWithWriting

given consumptionFromConvertibleToWritable [
  Y: [Y] =>> ConvertibleToWritable[Y, W, >-->]
  , W: Writable
  , >-->[- _, + _]: [>-->[- _, + _]] =>> ProgramWithWriting[W, >-->]
]: Consumption[Y, >-->] with 

  private val convertibleToWritable: ConvertibleToWritable[Y, W, >-->] = 
    summon[ConvertibleToWritable[Y, W, >-->]]
  import convertibleToWritable.`y>-->w`
  
  private val writing: Writing[W, >-->] = 
    summon[Writing[W, >-->]]
  import writing.`w>-->u`

  override def consume: Y >--> Unit =
    `y>-->w` >--> `w>-->u`
```

## `Resulting`

```scala
package psbp.internal.specification.resulting

private[psbp] trait Resulting[C[+ _]]:

  // declared

  private[psbp] def result[Z]: Z => C[Z]
```

## `Binding`

```scala
package psbp.internal.specification.binding

private[psbp] trait Binding[C[+ _]]:

  // declared

  private[psbp] def bind[Z, Y] (
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y]

  // defined

  private[psbp] def join[Z] (ccz: C[C[Z]]): C[Z] =
    bind(ccz, identity)

  extension [Z, Y] (cz: C[Z]) 
    private[psbp] def >=(`z=>cy`: => Z => C[Y]): C[Y] =
      bind(cz, `z=>cy`)
```

## `Computation`

```scala
package psbp.internal.specification.computation

import psbp.internal.specification.resulting.Resulting

import psbp.internal.specification.binding.Binding

private[psbp] trait Computation[C[+ _]] 
  extends Resulting[C] 
  with Binding[C]
```

## `programFromComputation`

```scala
package psbp.external.givens.computation

import psbp.external.specification.types.{ 
  &&
  , ||
}

import psbp.external.specification.function.foldSum

import psbp.external.specification.program.Program

import psbp.internal.specification.computation.Computation

private[psbp] given programFromComputation[
  C[+ _]: Computation]: Program[[Z, Y] =>> Z => C[Y]
] with
  
  private val computation: Computation[C] = 
    summon[Computation[C]]
  import computation.result

  private type `=>C`[-Z, +Y] = Z => C[Y]

  // defined

  override def identity[Z]: Z `=>C` Z =
    z =>
      result(z)

  override def andThen[Z, Y, X](
    `z>-->y`: Z `=>C` Y
    , `y>-->x`: => Y `=>C` X): Z `=>C` X =
    z =>
      `z>-->y`(z) >= 
        `y>-->x`      

  override def toProgram[Z, Y]: (Z => Y) => (Z `=>C` Y) = 
    `z=>y` => 
      z =>
        result(`z=>y`(z))

  override def construct[Z, Y, X](
      `z>-->y`: Z `=>C` Y
      , `z>-->x`: => Z `=>C` X): Z `=>C` (Y && X) =
    z =>
      `z>-->y`(z) >= { y => 
        `z>-->x`(z) >= { x =>
          result(y, x)
        }
      }

  override def conditionally[Z, Y, X](
      `y>-->z`: => Y `=>C` Z, 
      `x>-->z`: => X `=>C` Z): (Y || X) `=>C` Z =
    foldSum(`y>-->z`, `x>-->z`) 
```

## `NaturalTransformation`

```scala
package psbp.internal.specification.naturalTransformation

private[psbp] trait ~>[F[+ _], T[+ _]]:

  // declared

  private[psbp] def apply[Z]: F[Z] => T[Z]
```

## `ComputationTransformation`

```scala
package psbp.internal.specification.computation.transformation

import psbp.internal.specification.resulting.Resulting

import psbp.internal.specification.computation.Computation

import psbp.internal.specification.naturalTransformation.~>
  
private[psbp] trait ComputationTransformation[F[+ _]: Resulting, T[+ _]]
  extends Computation[T]:

  // declared

  private[psbp] val `f~>t`: F ~> T

  // defined
  
  override private[psbp] def result[Z]: Z => T[Z] = 

    import `f~>t`.{ 
      apply => `fz=>tz` 
    }

    val resulting: Resulting[F] = 
      summon[Resulting[F]]
    import resulting.{ 
      result => `z=>fz` 
    }
    
    `z=>fz` andThen `fz=>tz` 
```

## ``

```scala

```

## ``

```scala

```

## ``

```scala

```

## ``

```scala

```





