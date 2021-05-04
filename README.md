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

  val production = summon[Production[>-->, Z]]
  import production.{
    produce => `u>-->z`
  }

  val program = summon[Program[>-->]]
  import program.Let    

  val consumption = summon[Consumption[Z && Y, >-->]]
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

import scala.language.postfixOps

import psbp.external.specification.program.Program

import psbp.external.specification.reading.Readable

import psbp.external.specification.program.reading.ProgramWithReading

given programWithReading[
  R: Readable, 
  >-->[- _, + _]: Program
]: ProgramWithReading[R, >-->] with
 
  private val program: Program[>-->] = summon[Program[>-->]]

  export program.identity
  export program.andThen

  export program.toProgram
  export program.construct
  export program.conditionally

  override def read: Unit >--> R = 

    object function {

      val read: Unit => R =
        _ =>
          val readable = summon[Readable[R]]
          readable.r

    }

    function.read asProgram 
```

## `productionFromConvertibleFromReadable`

```scala
package psbp.external.specification.program.reading.givens

import psbp.external.specification.production.Production

import psbp.external.specification.reading.{
  Readable
  , ConvertibleFromReadable
  , Reading
}

import psbp.external.specification.program.reading.ProgramWithReading

given productionFromConvertibleFromReadable [
  Z: [Z] =>> ConvertibleFromReadable[R, Z, >-->]
  , R: Readable
  , >-->[- _, + _]: [>-->[- _, + _]] =>> ProgramWithReading[R, >-->]
]: Production[>-->, Z] with

  val convertibleFromReadable = summon[ConvertibleFromReadable[R, Z, >-->]]
  import convertibleFromReadable.`r>-->z`
    
  val reading = summon[Reading[R, >-->]]
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
 
  private val program: Program[>-->] = summon[Program[>-->]]

  export program.identity
  export program.andThen

  export program.toProgram
  export program.construct
  export program.conditionally

  private val writing: Writing[W, >-->] = summon[Writing[W, >-->]]

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

  private val convertibleToWritable = summon[ConvertibleToWritable[Y, W, >-->]]
  import convertibleToWritable.`y>-->w`
  
  private val writing = summon[Writing[W, >-->]]
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

## `ProgramFromComputation`

```scala
package psbp.external.implementation.computation

private[psbp] type ProgramFromComputation[C[+ _]] = [Z, Y] =>> Z => C[Y]
```

## `programFromComputation`

```scala
package psbp.external.implementation.computation.givens

import psbp.external.specification.types.{ 
  &&
  , ||
}

import psbp.external.specification.function.foldSum

import psbp.external.specification.program.Program

import psbp.internal.specification.computation.Computation

import psbp.external.implementation.computation.ProgramFromComputation

private[psbp] given programFromComputation[
  C[+ _]: Computation]: Program[ProgramFromComputation[C]
] with
  
  private val computation = summon[Computation[C]]
  import computation.result

  private type `=>C`[-Z, +Y] = ProgramFromComputation[C][Z, Y]

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

    val resulting = summon[Resulting[F]]
    import resulting.{ 
      result => `z=>fz` 
    }
    
    `z=>fz` andThen `fz=>tz` 
```

## `ReadingTransformed`

```scala
package psbp.internal.implementation.computation.transformation.reading

private[psbp] type ReadingTransformed[
  R
  , C[+ _]
] = [Y] =>> R ?=> C[Y] 
```

## `readingTransformedReading`

```scala
package psbp.internal.implementation.computation.transformation.reading.givens

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedReading[
  R: Readable
  , C[+ _]: Computation
]: Reading[
  R
  , [Z, Y] =>> Z => ReadingTransformed[R, C][Y]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>T` = [Z, Y] =>> Z => T[Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
  }

  override def read: Unit `=>T` R =
    _ =>
      val r: R = summon[R]
      resultF(r)
```

## `readingTransformedComputation`

```scala
package psbp.internal.implementation.computation.transformation.reading.givens

import psbp.external.specification.reading.Readable

import psbp.internal.specification.computation.Computation

import psbp.internal.specification.naturalTransformation.~>

import psbp.internal.specification.computation.transformation.ComputationTransformation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedComputation[
  R: Readable
  , C[+ _]: Computation
]: ComputationTransformation[C, ReadingTransformed[R, C]] 
  with Computation[ReadingTransformed[R, C]] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>T` = [Z, Y] =>> Z => T[Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
    , bind => bindF
  }

  override private[psbp] val `f~>t`: F ~> T = 
    new {
      def apply[Z]: F[Z] => T[Z] =
        fz =>
          bindF(fz, z => resultF(z))
    }  

  override private[psbp] def bind[Z, Y](
    tz: T[Z]
    , `z>=ty`: => Z => T[Y]
  ): T[Y] =
    bindF(tz, z => `z>=ty`(z) )
```

## `readingTransformedMaterialization`

```scala
package psbp.internal.implementation.computation.transformation.reading.givens

import psbp.external.specification.reading.Readable

import psbp.external.specification.materialization.Materialization

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

import psbp.internal.specification.computation.Computation

private[psbp] given readingTransformedMaterialization[
  R: Readable
  , C[+ _]: Computation
          : [C[+ _]] =>> Materialization[[Z, Y] =>> Z => C[Y], Z, Y]
  , Z, Y
]: Materialization[[Z, Y] =>> Z => ReadingTransformed[R, C][Y], Z, R ?=> C[Y]] with

  private type F[+Z] = C[Z]
  private type T[+Z] = ReadingTransformed[R, C][Z]

  private type `=>F`[-Z, +Y] = Z => F[Y]
  private type `=>T`[-Z, +Y] = Z => T[Y]

  private val materialization = summon[Materialization[`=>F`, Z, Y]]
  import materialization.{ 
    materialize => materializeF 
  }

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
    , bind => bindF 
  }

  override val materialize: (Unit `=>T` Unit) => Z ?=> (R ?=> C[Y]) =
    `u=>tu` =>
      bindF(
        `u=>tu`(())
        , _ => 
            resultF(materializeF(resultF))
      )
```

## `WritingTransformed`

```scala
package psbp.internal.implementation.computation.transformation.writing

import psbp.external.specification.types.&&

private[psbp] type WritingTransformed[
  W
  , C[+ _]
] = [Y] =>> C[W && Y] 
```

## `writingTransformedWriting`

```scala
package psbp.internal.implementation.computation.transformation.writing.givens

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

private[psbp] given writingTransformedWriting[
  W: Writable
  , C[+ _]: Computation
]: Writing[
  W
  , [Z, Y] =>> Z => WritingTransformed[W, C][Y]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = WritingTransformed[W, C][Y]

  private type `=>T` = [Z, Y] =>> Z => T[Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
  }

  override def write: W `=>T` Unit =
    w =>
      resultF((w, ()))
```

## `writingTransformedComputation`

```scala
package psbp.internal.implementation.computation.transformation.writing.givens

import psbp.external.specification.types.&&

import psbp.external.specification.writing.Writable

import psbp.internal.specification.computation.Computation

import psbp.internal.specification.naturalTransformation.~>

import psbp.internal.specification.computation.transformation.ComputationTransformation

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

private[psbp] given writingTransformedComputation[
  W : Writable
  , C[+ _]: Computation
]: ComputationTransformation[C,  WritingTransformed[W, C]] 
  with Computation[[Y] =>> WritingTransformed[W, C][Y]] with 

  private type F[+Y] = C[Y]
  private type T[+Y] = WritingTransformed[W, C][Y]

  private type `=>T` = [Z, Y] =>> Z => T[Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
    , bind => bindF
  }

  private val writable: Writable[W] = summon[Writable[W]]
  import writable.{
    empty
    , append
  }  

  override private[psbp] val `f~>t`: F ~> T = new {
    def apply[Z]: F[Z] => T[Z] =
      fz =>
        bindF(fz, z => resultF((empty, z)))
  }  

  override private[psbp] def bind[Z, Y](
    tz: T[Z]
    , `z=>ty`: => Z => T[Y]
  ): T[Y] =
    bindF(tz, (w1, z) =>
      val (w2, y): W && Y = `z=>ty`(z)
      resultF(append(w1, w2), y)
    )
```

## `writingTransformedMaterialization`

```scala
package psbp.internal.implementation.computation.transformation.writing.givens

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

import psbp.internal.specification.computation.Computation

private[psbp] given writingTransformedMaterialization[
  W: Writable
  , C[+ _]: Computation
          : [C[+ _]] =>> Materialization[[Z, Y] =>> Z => C[Y], Z, Y]
  , Z, Y
]: Materialization[[Z, Y] =>> Z => WritingTransformed[W, C][Y], Z, C[(W, Y)]] with

  private type F[+Z] = C[Z]
  private type T[+Z] = WritingTransformed[W, C][Z]

  private type `=>F`[-Z, +Y] = Z => F[Y]
  private type `=>T`[-Z, +Y] = Z => T[Y]

  private val Materialization: Materialization[`=>F`, Z, Y] = summon[Materialization[`=>F`, Z, Y]]
  import Materialization.{ 
    materialize => materializeF 
  }

  private val computation: summon[Computation[F]]
  import computation.{ 
    result => resultF
    , bind => bindF 
  }  

  override val materialize: (Unit `=>T` Unit) => Z ?=> C[(W, Y)] =
    `u=>tu` =>
      bindF(
        `u=>tu`(())
        , (w, u) =>
            resultF((w, materializeF(resultF)))
      )
```

## `readingTransformedWriting`

```scala
package psbp.internal.implementation.computation.transformation.writing.reading.givens

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedWriting[
  R: Readable
  , W: Writable
  , C[+ _]: [C[+ _]] =>> Writing[W, [Z, Y] =>> Z => C[Y]]
]: Writing[
  W
  , [Z, Y] =>> Z => ReadingTransformed[R, C][Y]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>F` = [Z, Y] =>> Z => F[Y]
  private type `=>T` = [Z, Y] =>> Z => T[Y]

  private val writing = summon[Writing[W, `=>F`]]
  import writing.{
    write => writeF
  }

  override def write: W `=>T` Unit =
    w =>
      writeF(w)
```

## `Active`

```scala
package psbp.external.implementation.active

type Active[+Y] = Y

type `=>A`[-Z, +Y] = Z => Active[Y]
```

## ``

```scala
package psbp.external.implementation.active.givens

import psbp.external.specification.program.Program

import psbp.external.implementation.active.{
  Active
  , `=>A`
}

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.computation.givens.programFromComputation

private[psbp] given activeComputation: Computation[Active] with

  private[psbp] def result[Z]: Z => Active[Z] =
    z =>
      z

  private[psbp] def bind[Z, Y](
    cz: Active[Z]
    , `z=>cy`: => Z => Active[Y]
  ): Active[Y] =
    `z=>cy`(cz)

given activeProgram: Program[`=>A`] = 
  programFromComputation[Active]
```

## `activeProgram`

```scala
package psbp.external.implementation.active.givens

import psbp.external.specification.program.Program

import psbp.internal.specification.computation.Computation

import psbp.external.implementation.computation.givens.programFromComputation

import psbp.external.implementation.active.{
  Active
  , `=>A`
}

private[psbp] given activeComputation: Computation[Active] with

  private[psbp] def result[Z]: Z => Active[Z] =
    z =>
      z

  private[psbp] def bind[Z, Y](
    cz: Active[Z]
    , `z=>cy`: => Z => Active[Y]
  ): Active[Y] =
    `z=>cy`(cz)

given activeProgram: Program[`=>A`] = 
  programFromComputation[Active]
```

## `activeMaterialization`

```scala
package psbp.external.implementation.active.givens

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.`=>A`

given activeMaterialization: Materialization[`=>A`, Unit, Unit] with
  val materialize: (Unit `=>A` Unit) => Unit ?=> Unit =
    `u>-->u` => 
      val u = summon[Unit]
      `u>-->u`(u)
```

## `ActiveReading`

```scala
package psbp.external.implementation.active.reading

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

import psbp.external.implementation.active.Active

type ActiveReading[R] = [Y] =>> ReadingTransformed[R, Active][Y] 

type `=>AR`[R] = [Z, Y] =>> Z => ActiveReading[R][Y]
```

## `activeReadingReading`

```scala
package psbp.external.implementation.active.reading.givens

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.implementation.active.Active

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedReading

import psbp.external.implementation.active.reading.`=>AR`

given activeReadingReading[R: Readable]: Reading[R, `=>AR`[R]] = 
  readingTransformedReading[R, Active]
```

## `activeReadingProgram`

```scala
package psbp.external.implementation.active.reading.givens

import psbp.external.specification.program.Program

import psbp.external.specification.reading.Readable

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.reading.{
    ActiveReading, `=>AR`
}

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.computation.givens.programFromComputation

import psbp.external.implementation.active.givens.activeComputation

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedComputation

private[psbp] given activeReadingComputation[R: Readable]: Computation[ActiveReading[R]] = 
  readingTransformedComputation[R, Active]

given activeReadingProgram[R: Readable]: Program[`=>AR`[R]] =
  programFromComputation[ActiveReading[R]]
```

## `activeReadingMaterialization`

```scala
package psbp.external.implementation.active.reading.givens

import psbp.external.specification.reading.Readable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.reading.`=>AR`

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.external.implementation.active.givens.activeMaterialization

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedMaterialization

given activeReadingMaterialization[R: Readable]: Materialization[`=>AR`[R], Unit, R ?=> Unit] =
  readingTransformedMaterialization[R, Active, Unit, Unit]
```

## `ActiveWriting`

```scala
package psbp.external.implementation.active.writing

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

import psbp.external.implementation.active.Active

type ActiveWriting[W] = [Y] =>> WritingTransformed[W, Active][Y] 

type `=>AW`[W] = [Z, Y] =>> Z => ActiveWriting[W][Y]
```

## `activeWritingWriting`

```scala
package psbp.external.implementation.active.writing.givens

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.`=>AW`

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.internal.implementation.computation.transformation.writing.givens.writingTransformedWriting

given activeWritingWriting[W: Writable]: Writing[W, `=>AW`[W]] = 
  writingTransformedWriting[W, Active]
```

## `activeWritingComputation`

```scala
package psbp.external.implementation.active.writing.givens

import psbp.external.specification.program.Program

import psbp.external.specification.writing.Writable

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.{
    ActiveWriting, `=>AW`
}

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.computation.givens.programFromComputation

import psbp.external.implementation.active.givens.activeComputation

import psbp.internal.implementation.computation.transformation.writing.givens.writingTransformedComputation

private[psbp] given activeWritingComputation[W: Writable]: Computation[ActiveWriting[W]] = 
  writingTransformedComputation[W, Active]

given activeWritingProgram[W: Writable]: Program[`=>AW`[W]] =
  programFromComputation[ActiveWriting[W]]
```

## `activeWritingMaterialization`

```scala
package psbp.external.implementation.active.writing.givens

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.`=>AW`

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.external.implementation.active.givens.activeMaterialization

import psbp.internal.implementation.computation.transformation.writing.givens.writingTransformedMaterialization

given activeWritingMaterialization[W: Writable]: Materialization[`=>AW`[W], Unit, (W, Unit)] =
  writingTransformedMaterialization[W, Active, Unit, Unit]
```

## `ActiveWritingReading`

```scala
package psbp.external.implementation.active.writing.reading

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.ActiveWriting

type ActiveWritingReading[W, R] = [Y] =>> ReadingTransformed[R, ActiveWriting[W]][Y]

type `=>AWR`[W, R] = [Z, Y] =>> Z => ActiveWritingReading[W, R][Y]
```

## `activeWritingReadingWriting`

```scala
package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.specification.reading.Readable

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.`=>AWR`

// givens

import psbp.external.implementation.active.writing.givens.activeWritingWriting

import psbp.internal.implementation.computation.transformation.writing.reading.givens.readingTransformedWriting

given activeWritingReadingWriting[
  W: Writable
  , R: Readable
]: Writing[W, `=>AWR`[W, R]] = 
  readingTransformedWriting[R, W, ActiveWriting[W]]
```

## `activeWritingReadingReading`

```scala
package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.reading.{
  Readable
  , Reading
}

import psbp.external.specification.writing.Writable

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.`=>AWR`

// givens

import psbp.external.implementation.active.writing.givens.activeWritingComputation

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedReading

given activeWritingReadingReading[
  W: Writable
  , R: Readable
]: Reading[R, `=>AWR`[W, R]] = 
  readingTransformedReading[R, ActiveWriting[W]]
```

## `activeWritingReadingProgram`

```scala
package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.program.Program

import psbp.external.specification.reading.Readable

import psbp.external.specification.writing.Writable

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.{
  ActiveWritingReading
  , `=>AWR`
}

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.computation.givens.programFromComputation

import psbp.external.implementation.active.writing.givens.activeWritingComputation

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedComputation

private[psbp] given activeWritingReadingComputation[
  W: Writable
  , R: Readable
]: Computation[ActiveWritingReading[W, R]] = 
  readingTransformedComputation[R, ActiveWriting[W]]

given activeWritingReadingProgram[
  W: Writable
  , R: Readable
]: Program[`=>AWR`[W, R]] = 
  programFromComputation[ActiveWritingReading[W, R]]
```

## `activeWritingReadingMaterialization`

```scala
package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.reading.Readable

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.{
  ActiveWritingReading
  , `=>AWR`
}

// givens

import psbp.external.implementation.active.writing.givens.activeWritingComputation

import psbp.external.implementation.active.writing.givens.activeWritingMaterialization 

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedMaterialization

given activeWritingReadingMaterialization[
  W: Writable
  , R: Readable
]: Materialization[
  `=>AWR`[W, R]
  , Unit
  , R ?=> (W, (W, Unit))
] = readingTransformedMaterialization[R, ActiveWriting[W], Unit, (W, Unit)]
```

## `StdIn`

```scala
package psbp.external.implementation.stdIn

case class StdIn[Z](`u=>z`: Unit => Z)
```

<!-- convertibleFromStdInReadable -->

## `StdOut`

```scala
package psbp.external.implementation.stdOut

package psbp.external.implementation.stdOut

case class StdOut(`u=>u`: Unit => Unit)
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

## ``

```scala

```

## ``

```scala

```

## ``

```scala

```




