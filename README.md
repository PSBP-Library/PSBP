# `PSBP`

One way to teach programming is by means of a *programming library*.

The name of the `Scala` library is `PSBP`, standing for, *Program Specification Based Programming*.

The library has an external part, *specifying* what *programs* are all about.

Think of programs as generalizations of functions.

## `trait`'s

Specifications are `trait`'s.

## `Classification`

Specifying programs is done by classifying*, or *categorizing* them.

`Classification` is based on `Identity` and `Composition`.

```scala
package psbp.external.specification.classification

import psbp.external.specification.identity.Identity

import psbp.external.specification.composition.Composition

trait Classification[>-->[- _, + _]]
  extends Identity[>-->]
  , Composition[>-->]
```

`Classification` simply combines `Identity` and `Composition`.

A *program* has type `Z >--> Y` for a *binary type constructor* `>-->[- _, + _]` that is *contravariant* in its *first type parameter* and *covariant* in its *second type paramenter*.

The variance of `>-->[- _, + _]` corresponds to the *requiring less and providing more is ok* principle. 

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

`Identity` specifies, using `identity`, a *pure program* that yields its *argument* as a *result*.

It is convenient to define a handful of *typeful names* like  `` `z>-->z` ``, that turn out to be suggestive when dealing with generic programs.

`` `u>-->u` `` reserved for the `Unit` type.

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

`Composition` specifies, using `andThen` and corresponding `extension` `>-->`, that programs can be *composed sequentially*.

Note that the second parameter of `andThen` is a *by name* one.

Note that program `` `z>-->y` >--> `y>-->x` `` has type `Z >--> X`, as suggested by the typeful names.

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

`Function` specifies, using `toProgram` and corresponding `extension` `asProgram`, that *pure functions* of type `Z => Y` can be used *as pure programs* of type `Z >--> Y`.

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

where

```scala
package psbp.external.specification.types

// product

type &&[+Z, +Y] = (Z, Y)

// ...
```

`Construction` specifies, using `construct` and corresponding `extension` `&&`, that programs can *construct product based data*.

Note that the second parameter of `construct` is a *by name* one.

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

where

```scala
package psbp.external.specification.types

// ...

// sum

enum ||[+Z, +Y]:
  case Left(z: Z) extends (Z || Y)
  case Right(y: Y) extends (Z || Y)

// ...  
```

`Condition` specifies, using `contitionally` and corresponding `extension` `||`, that programs can *conditionally perform sum based logic*.

Note that the both parameters of `construct` are a *by name* ones.

## Utilities

Many definitions use *auxiliary pure functions* and corresponding *auxiliary pure programs* like *projections from products* and *injections to sums*.

```scala
package psbp.external.specification.function

import scala.language.postfixOps

import psbp.external.specification.types.{ 
  &&
  , ||
}

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

// ...

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

// ...  
```

Many more auxiliary pure functions and corresponding auxiliary pure programs are used, for example

```scala
package psbp.external.specification.function

// ...

// condition

// ...

def `(z&&b)=>(z||z)`[Z]: (Z && Boolean) => (Z || Z) = {
  case (z, true) => 
    Left(z)
  case (z, false) => 
    Right(z) 
} 

// ...

// condition

// ...

def `(z&&b)>-->(z||z)`[
  Z
  , >-->[- _, + _]: Function
]: (Z && Boolean) >--> (Z || Z) =
  `(z&&b)=>(z||z)` asProgram

// ...  
```

`` `(z&&b)=>(z||z)` ``  decides to go `Left` or to go `Right` with `z` based upon a `Boolean`.

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
```

`Program` simply combines `Classification` and `Function`, `Construction` and `Condition`.

`&&&` is a defined `extension` that is more complex versions of the declared `extension` `&&`.

`|||` is a defined `extension` that is more complex versions of the declared `extension` `||`.

`Let { ... } In { ... }` defines how programs can use *local values*.

`If() { ... } Else { ... }` defines how programs can use *if-then-else logic*.

It turns that `Let { ... } In { ... }` has the same power of expression as product based data construction.

It turns that `If() { ... } Else { ... }` has the same power of expression as sum based conditional logic.

## `Production` and `Consumption`

Programming is not only about *pure functions*, *transforming arguments to yield *results*, *constructing data* and *performing conditional logic*.

Also *impure* capabilities are part of the programming world.

For example, *external* impure capabilities, that *interact with the outside world*.

But also, *internal* impure capabilities, like *dealing with randomness*.

Two important such capabilities are *production of data*, for example from *standard input*, and *consumption of data*, for example to *standard output*.

```scala
package psbp.external.specification.production

trait Production[>-->[- _, + _], +Z]:

  // declared

  def produce: Unit >--> Z

  // defined

  def `u>-->z`: Unit >--> Z =
    produce
```

`Production` specifies, using `produce` and corresponding `extension` `` `u>-->z` ``, that programs can *produce data*.

```scala
package psbp.external.specification.consumption

trait Consumption[-Y, >-->[- _, + _]]:

  // declared

  def consume: Y >--> Unit

  // defined

  def `y>-->u`: Y >--> Unit =
    consume
```

`Consumption` specifies, using `consume` and corresponding `extension` `` `y>-->u` ``, that programs can *consume data*.

## `toMain`

A *main program* is program of type `Unit >--> Unit`.

Main programs interact with each other using the outside world.

```scala
package psbp.external.specification.program.main

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.production.Production

import psbp.external.specification.consumption.Consumption

def toMain[
  Z, Y
  , >-->[- _, + _]
    : [>-->[- _, + _]] =>> Production[>-->, Z]
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

Using `toMain` a program can be combined with production and consumption to obtain a *main program*.

Note that, from a produced argument of type `Z`, `Let { ... }` constructs a local result value of type `Y` that, together with the produced argument, is available for consumption by `In { ... }`.

When using `>-->` instead of `Let { ... } In { ... }` the produced argument would not be available for consumption.

## `Materialization`

```scala
package psbp.external.specification.materialization

trait Materialization[>-->[- _, + _], -Z, +Y]:

  // declared

  val materialize: (Unit >--> Unit) => Z ?=> Y
```

Main programs are *materialized* to functions of type `Z ?=> Y`.

A function of type `Z ?=> Y` yields a result of type `Y` when `given` an argument of type `Z`.

The types `Z` and `Y` reflect the impure capabilities of `>-->[- _, + _]`.

## `given`'s

Program specifications are *implemented* as `given`'s and made available in `@main` code using *dependency injection* `import`.

`@main` code then uses *materialized implemented main programs*.

## `Reading` and `Writing`

Production and consumption are related to *reading* and *writing*.

```scala
package psbp.external.specification.reading

trait Reading[R, >-->[- _, + _]]:

  // declared

  def read: Unit >--> R

  // defined

  def `u>-->r`: Unit >--> R =
    read
```

`Reading` specifies, using `read` and corresponding `extension` `` `u>-->r` ``,  that programs can *read data*.

```scala
package psbp.external.specification.program.reading.givens

import psbp.external.specification.production.Production

import psbp.external.specification.reading.Reading

given productionFromReading [
  Z: [Z] =>> Reading[Z, >-->]
  , >-->[- _, + _]  
]: Production[>-->, Z] with
    
  val reading = summon[Reading[Z, >-->]]
  import reading.read
  
  override def produce: Unit >--> Z =
    read
```

`given productionFromReading` is completely trivial.

Note that it is also possible to read *in the middle* of a program, not only *at the beginning* of a main program to produce.

## `Writable`

```scala
package psbp.external.specification.writing

import psbp.external.specification.types.&&

trait Writable[W]:

  // declared

  def nothing: W

  def append: (W && W) => W
  
  // defined 

  extension (w1: W) 
    def +(w2: W): W =
      append(w1, w2)
```

`Writable` specifies, using `nothing`, that *nothing has been written*, and using `append`, and corresponding `extension` `+` that *written things can be appended*.

## `ConvertibleToWritable`

```scala
package psbp.external.specification.writing

trait ConvertibleToWritable[
  -Y
  , W: Writable, 
  >-->[- _, + _]
]:

  // declared

  def convert: Y >--> W

  // defined
  
  def `y>-->w`: Y >--> W =
    convert
```

`ConvertibleToWritable` specifies, using `convert` and corresponding `extension` `` `y>-->w` ``, that programs can *convert data to something that can be written*.

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

`Writing` specifies, using `write` and corresponding `extension` `` `w>-->u` ``, that programs can *write things that can be written*.

```scala
package psbp.external.specification.program.writing.givens

import psbp.external.specification.consumption.Consumption

import psbp.external.specification.program.Program

import psbp.external.specification.writing.{
  Writable
  , ConvertibleToWritable
  , Writing
}

given consumptionFromConvertibleToWritable[
  Y: [Y] =>> ConvertibleToWritable[Y, W, >-->]
  , W: Writable
     : [W] =>> Writing[W, >-->]
  , >-->[- _, + _]: Program 
]: Consumption[Y, >-->] with 

  private val convertibleToWritable = summon[ConvertibleToWritable[Y, W, >-->]]
  import convertibleToWritable.`y>-->w`
  
  private val writing = summon[Writing[W, >-->]]
  import writing.`w>-->u`

  override def consume: Y >--> Unit =
    `y>-->w` >--> `w>-->u`
```

`given consumptionFromConvertibleToWritable` is a bit more involved than `given productionFromReading`.

To mention one thing, it requires sequential composition.

Note the usage of typeful names in `` `y>-->w` >--> `w>-->u` `` suggesting a progam of type `Y >--> Unit`.

Note that it is also possible to write *in the middle* of a program, not only *at the end* of a main program to consume.

## `Computation`

The library has an internal part, *specifying* what *computations* are all about.

Think of computations as generalizations of *expressions* and of *executing* computations as a generalization of *evaluating* expressions.

```scala
package psbp.internal.specification.computation

import psbp.internal.specification.resulting.Resulting

import psbp.internal.specification.binding.Binding

private[psbp] trait Computation[C[+ _]] 
  extends Resulting[C] 
  with Binding[C]
```

`Computation` simply combines `Resulting` and `Binding`.

A *computation* has type `C[Y]` for a *unary type constructor* `C[+ _]` that is *covariant* in its *type paramenter*.

The variance expresses the *providing more is ok* principle. 

## `Resulting`

```scala
package psbp.internal.specification.resulting

private[psbp] trait Resulting[C[+ _]]:

  // declared

  private[psbp] def result[Z]: Z => C[Z]
```

`Resulting` specifies, using `result`, a *pure computation* that yields its *argument* as a *result*.

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

`Binding` specifies, using `bind`, that the result yielded by an *inner computation* can be *bound* to a *continuation* that turns it into an *outer computation*. 

## `ProgramFromComputation`

```scala
package psbp.external.implementation.computation

private[psbp] type ProgramFromComputation[C[+ _]] = [Z, Y] =>> Z => C[Y]
```

`ProgramFromComputation` is a convenient type synonym.

## `foldSum`

```scala
package psbp.external.specification.function

// ...

// condition

def foldSum[Z, Y, X]: ((Y => Z) && (X => Z)) => (Y || X) => Z =
  (`z=>x`, `y=>x`) => 
    case Left(z) => `z=>x`(z)
    case Right(y) =>`y=>x`(y) 
    
// ...    
```

`foldSum` is a convenient auxiliary function.

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

private[psbp] given programFromComputation[C[+ _]: Computation]: Program[ProgramFromComputation[C]] with
  
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

Just like *functions can be defined in terms of expressions*, *programming capabilities* can be defined in terms of *computing capabilities*.

## `NaturalTransformation`

```scala
package psbp.internal.specification.naturalTransformation

private[psbp] trait ~>[-F[+ _], +T[+ _]]:

  // declared

  private[psbp] def apply[Z]: F[Z] => T[Z]
```

A *natural transformation* has type `F ~> T` for a *binary type-constructor constructor* `~>[-F[+ _], +T[+ _]]`.

Think of a natural transformation as a *generic function* that works at the *type constructor* level instead of at the *type level*.

## `ComputationTransformation`

```scala
package psbp.internal.specification.computation.transformation

import psbp.internal.specification.resulting.Resulting

import psbp.internal.specification.naturalTransformation.~>
  
private[psbp] trait ComputationTransformation[
  F[+ _]: Resulting 
  , T[+ _]
] extends Resulting[T]:

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

A *computation transformation* uses a natural transformation `f~>t` of type `F ~> T`.

`result` for `T` can naturally be defined in terms of `result` of `F` and `f~>t`.

Note that usage of typeful names, this time for functions.

## `ReadingTransformed`

```scala
package psbp.internal.implementation.computation.transformation.reading

private[psbp] type ReadingTransformed[
  R
  , C[+ _]
] = [Y] =>> R ?=> C[Y] 
```

Transforming computations so that they can read data uses the type `ReadingTransformed[R, C]`.

## `readingTransformedReading`

```scala
package psbp.internal.implementation.computation.transformation.reading.givens

import psbp.external.specification.reading.Reading

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedReading[
  R
  , C[+ _]: Computation
]: Reading[
  R
  , ProgramFromComputation[ReadingTransformed[R, C]]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
  }

  override def read: Unit `=>T` R =
    _ =>
      resultF(summon[R])
```

Defining `read` for `` `=>T` `` is simple, just use `summon[R]`, the supposed to be `given` value of type `R`, and turn it into a computation using `result` of `F`.

## `readingTransformedComputation`

```scala
package psbp.internal.implementation.computation.transformation.reading.givens

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

import psbp.internal.specification.naturalTransformation.~>

import psbp.internal.specification.computation.transformation.ComputationTransformation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedComputation[
  R
  , C[+ _]: Computation
]: ComputationTransformation[C, ReadingTransformed[R, C]] 
  with Computation[ReadingTransformed[R, C]] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    bind => bindF
  }

  override private[psbp] val `f~>t`: F ~> T = 
    new {
      def apply[Z]: F[Z] => T[Z] =
        fz => 
          fz
    }  

  override private[psbp] def bind[Z, Y](
    tz: T[Z]
    , `z=>ty`: => Z => T[Y]
  ): T[Y] =
    bindF(tz, z => `z=>ty`(z))
```

Defining `f~>t` is simple, the type system can deal with the supposed to be `given` value of type `R` to check type correctness.

Defining `bind` is simple as well, again the type system can deal with the supposed to be `given` value of type `R` to check type correctness.

## `readingTransformedMaterialization`

```scala
package psbp.internal.implementation.computation.transformation.reading.givens

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[psbp] given readingTransformedMaterialization[
  R
  , C[+ _]: Computation
          : [C[+ _]] =>> Materialization[
            ProgramFromComputation[C]
            , Z
            , Y
            ]
  , Z, Y
]: Materialization[
  ProgramFromComputation[ReadingTransformed[R, C]]
  , Z, 
  R ?=> C[Y]
] with

  private type F[+Z] = C[Z]
  private type T[+Z] = ReadingTransformed[R, C][Z]

  private type `=>F`= [Z, Y] =>> ProgramFromComputation[F][Z, Y]
  private type `=>T`= [Z, Y] =>> ProgramFromComputation[T][Z, Y]

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

Defining `materialize` for `T` in terms of `materialize` of `F` is a bit more complex.

It is the only reasonable definition that keeps the type system happy.

## `WritingTransformed`

```scala
package psbp.internal.implementation.computation.transformation.writing

import psbp.external.specification.types.&&

private[psbp] type WritingTransformed[
  W
  , C[+ _]
] = [Y] =>> C[W && Y] 
```

Transforming computations so that they can write things that can be written uses the type `WritingTransformed[W, C]`.

## `writingTransformedWriting`

```scala
package psbp.internal.implementation.computation.transformation.writing.givens

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

private[psbp] given writingTransformedWriting[
  W: Writable
  , C[+ _]: Computation
]: Writing[
  W
  , ProgramFromComputation[WritingTransformed[W, C]]
] with

  private type F[+Y] = C[Y]
  private type T[+Y] = WritingTransformed[W, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
  }

  override def write: W `=>T` Unit =
    w =>
      resultF((w, ()))
```

Defining `write` for `` `=>T` `` is simple, just tuple `w` with `()` to `(w, ())`, and turn it into a computation using `result` of `F`.

## `writingTransformedComputation`

```scala
package psbp.internal.implementation.computation.transformation.writing.givens

import psbp.external.specification.types.&&

import psbp.external.specification.writing.Writable

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

import psbp.internal.specification.naturalTransformation.~>

import psbp.internal.specification.computation.transformation.ComputationTransformation

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

private[psbp] given writingTransformedComputation[
  W : Writable
  , C[+ _]: Computation
]: ComputationTransformation[
  C
  ,  WritingTransformed[W, C]
] with Computation[WritingTransformed[W, C]] with 

  private type F[+Y] = C[Y]
  private type T[+Y] = WritingTransformed[W, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    result => resultF
    , bind => bindF
  }

  private val writable = summon[Writable[W]]
  import writable.{
    nothing
    , append
  }  

  override private[psbp] val `f~>t`: F ~> T = new {
    def apply[Z]: F[Z] => T[Z] =
      fz =>
        bindF(fz, z => resultF((nothing, z)))
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

Defining `f~>t` is simple, just use `nothing`.

Defining `bind` is simple as well, just use `append`.

## `writingTransformedMaterialization`

```scala
package psbp.internal.implementation.computation.transformation.writing.givens

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.specification.computation.Computation

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

private[psbp] given writingTransformedMaterialization[
  W: Writable
  , C[+ _]: Computation
          : [C[+ _]] =>> Materialization[
            ProgramFromComputation[C]
            , Z
            , Y
            ]
  , Z, Y
]: Materialization[
  ProgramFromComputation[WritingTransformed[W, C]]
  , Z
  , C[(W, Y)]
] with

  private type F[+Z] = C[Z]
  private type T[+Z] = WritingTransformed[W, C][Z]

  private type `=>F`= [Z, Y] =>> ProgramFromComputation[F][Z, Y]
  private type `=>T`= [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val Materialization = summon[Materialization[`=>F`, Z, Y]]
  import Materialization.{ 
    materialize => materializeF 
  }

  private val computation = summon[Computation[F]]
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

Again, defining `materialize` for `T` in terms of `materialize` of `F` is a bit more complex.

Again, it is the only reasonable definition that keeps the type system happy.

## `readingTransformedWriting`

So what about combining reading and writing?

`ReadingTransformed` preserves `Writing`.

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

Defining `write` for `` `=>T` `` is simple, just use `write` of `F`, the type system can deal with the supposed to be `given` value of type `R`.

All machinery is now available to define a `given` implementation of the `Program`, `Reading` and `Writing`, and corresponding `Materialization` specification `trait`'s.

Let's start with `Program`, and corresponding `Materialization`.

## `Active`

```scala
package psbp.external.implementation.active

import psbp.external.implementation.computation.ProgramFromComputation

type Active = [Y] =>> Y

type `=>A`= [Z, Y] =>> ProgramFromComputation[Active][Z, Y]
```

Think of an *active computation* of type `Active[Y]` as an expression and of an *active program* of type `` Z `=>A` Y `` as a function.

## `activeComputation`

```scala
package psbp.external.implementation.active.givens

import psbp.external.specification.program.Program

import psbp.external.implementation.active.{
  Active
  , `=>A`
}

import psbp.internal.specification.computation.Computation

private[psbp] given activeComputation: Computation[Active] with

  private[psbp] def result[Z]: Z => Active[Z] =
    z =>
      z

  private[psbp] def bind[Z, Y](
    cz: Active[Z]
    , `z=>cy`: => Z => Active[Y]
  ): Active[Y] =
    `z=>cy`(cz)

// givens

// import psbp.external.implementation.computation.givens.programFromComputation

// given activeProgram: Program[`=>A`] = 
//   programFromComputation[Active]
```

Defining `result` and `bind` is trivial.

## `activeMaterialization`

```scala
package psbp.external.implementation.active.givens

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.`=>A`

given activeMaterialization: Materialization[`=>A`, Unit, Unit] with
  val materialize: (Unit `=>A` Unit) => Unit ?=> Unit =
    `u>-->u` => 
      `u>-->u`(summon[Unit])
```

Defining `materialize` is trivial.

Let's add `Reading` and `Writing`, and corresponding `Materialization`.

## `ActiveReading`

```scala
package psbp.external.implementation.active.reading

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

import psbp.external.implementation.active.Active

type ActiveReading[R] = [Y] =>> ReadingTransformed[R, Active][Y] 

type `=>AR`[R] = [Z, Y] =>> ProgramFromComputation[ActiveReading[R]][Z, Y]
```

## `ActiveWriting`

```scala
package psbp.external.implementation.active.writing

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.implementation.computation.transformation.writing.WritingTransformed

import psbp.external.implementation.active.Active

type ActiveWriting[W] = [Y] =>> WritingTransformed[W, Active][Y] 

type `=>AW`[W] = [Z, Y] =>> ProgramFromComputation[ActiveWriting[W]][Z, Y]
```

## `ActiveWritingReading`

```scala
package psbp.external.implementation.active.writing.reading

import psbp.external.implementation.computation.ProgramFromComputation

import psbp.internal.implementation.computation.transformation.reading.ReadingTransformed

import psbp.external.implementation.active.writing.ActiveWriting

type ActiveWritingReading[W, R] = [Y] =>> ReadingTransformed[R, ActiveWriting[W]][Y]

type `=>AWR`[W, R] = [Z, Y] =>> ProgramFromComputation[ActiveWritingReading[W, R]][Z, Y]
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

The code above, and also the one below simply consists of defining a new `given` in terms of existing `given`'s that are made available using dependency injection by `import`.

## `activeWritingComputation`

```scala
package psbp.external.implementation.active.writing.givens

import psbp.external.specification.program.Program

import psbp.external.specification.writing.Writable

import psbp.external.implementation.active.Active

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.internal.specification.computation.Computation

// givens

import psbp.external.implementation.active.givens.activeComputation

import psbp.internal.implementation.computation.transformation.writing.givens.writingTransformedComputation

private[psbp] given activeWritingComputation[W: Writable]: Computation[ActiveWriting[W]] = 
  writingTransformedComputation[W, Active]

// import psbp.external.implementation.active.writing.`=>AW`
  
// givens

// import psbp.external.implementation.computation.givens.programFromComputation

// given activeWritingProgram[W: Writable]: Program[`=>AW`[W]] =
//   programFromComputation[ActiveWriting[W]]
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

## `activeWritingReadingReading`

```scala
package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.writing.Writable

import psbp.external.specification.reading.Reading

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.`=>AWR`

// givens

import psbp.external.implementation.active.writing.givens.activeWritingComputation

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedReading

given activeWritingReadingReading[
  W: Writable
  , R
]: Reading[R, `=>AWR`[W, R]] = 
  readingTransformedReading[R, ActiveWriting[W]]
```

## `activeWritingReadingWriting`

```scala
package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.writing.{
  Writable
  , Writing
}

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.`=>AWR`

// givens

import psbp.external.implementation.active.writing.givens.activeWritingWriting

import psbp.internal.implementation.computation.transformation.writing.reading.givens.readingTransformedWriting

given activeWritingReadingWriting[
  W: Writable
  , R
]: Writing[W, `=>AWR`[W, R]] = 
  readingTransformedWriting[R, W, ActiveWriting[W]]
```

## `activeWritingReadingProgram`

```scala
package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.program.Program

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
  , R
]: Computation[ActiveWritingReading[W, R]] = 
  readingTransformedComputation[R, ActiveWriting[W]]

given activeWritingReadingProgram[
  W: Writable
  , R
]: Program[`=>AWR`[W, R]] = 
  programFromComputation[ActiveWritingReading[W, R]]
```

Note that, this time, we also define `given activeWritingReadingProgram`.

## `activeWritingReadingMaterialization`

```scala
package psbp.external.implementation.active.writing.reading.givens

import psbp.external.specification.writing.Writable

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.active.writing.ActiveWriting

import psbp.external.implementation.active.writing.reading.`=>AWR`

// givens

import psbp.external.implementation.active.writing.givens.activeWritingComputation

import psbp.external.implementation.active.writing.givens.activeWritingMaterialization 

import psbp.internal.implementation.computation.transformation.reading.givens.readingTransformedMaterialization

given activeWritingReadingMaterialization[
  W: Writable
  , R
]: Materialization[
  `=>AWR`[W, R]
  , Unit
  , R ?=> (W, (W, Unit))
] = readingTransformedMaterialization[R, ActiveWriting[W], Unit, (W, Unit)]
```

## Programs as proofs

Note that, for example, `given activeWritingReadingMaterialization` is defined in terms of `given activeWritingComputation` and `given activeWritingMaterialization` and `given readingTransformedMaterialization`.

Think of declaring a specification `trait` as stating a *theorem*.

Think of defining a `given` implementation of the specifcation as *proving the theorem*.

All proofs are *constructive* in the sense that they *prove existence using a definition*.

## `StdIn` and `StdOut`

We are not ready yet.

We need to define what `R` and `W` are.

For now we use *standard input* to `read` `R`'s from, and `Writable` *standard output* to ``convert` `W`'s to.

Reading from standard input is simple

```scala
package psbp.external.implementation.givens

import scala.language.postfixOps

import psbp.external.specification.program.Program

import psbp.external.specification.reading.Reading

given reading[
  Z
  , >-->[- _, + _]: Program 
](using z: Z): Reading[Z, >-->] with

  object function {
    val read: Unit => Z =
      _ => 
        z
  }

  override def read: Unit >--> Z =
    function.read asProgram
```

and the value `z` that `reading` is `using` to be `read` can come from `scala.io.StdIn` as follows

```scala
package psbp.external.implementation.stdIn

case class StdIn[Z](effect: Unit => Z)
```

An argument of type `BigInt` can then come from `scala.io.StdIn` as follows

```scala
package psbp.external.implementation.stdIn

val stdInBigIntArgument: StdIn[BigInt] =
  StdIn(effect = { _ => BigInt(scala.io.StdIn.readInt) })
```

Writing to standard output is a bit more involved

```scala
package psbp.external.implementation.stdOut

case class StdOut(effect: Unit => Unit)
```

```scala
package psbp.external.implementation.stdOut.givens

import psbp.external.specification.types.&&

import psbp.external.specification.writing.Writable

import psbp.external.implementation.stdOut.StdOut

given stdOutWritable: Writable[StdOut] with

  // defined

  override def nothing: StdOut = 
    StdOut(effect = identity )

  override def append: (StdOut && StdOut) => StdOut =
    case (StdOut(firstEffect), StdOut(secondEffect)) =>
      StdOut(effect = firstEffect andThen secondEffect)
```

`given stdOutWritable` defines `StdOut` to be `Writable`.

An argument of type `Z` and result of type `Y` can then convert to `StdInOut` as follows

```scala
package psbp.external.implementation.stdOut.givens

import scala.language.postfixOps

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.writing.{
  ConvertibleToWritable
}

import psbp.external.implementation.stdOut.StdOut

given argumentAndResultConvertibleToStdOutWritable[
  Z
  , Y
  , >-->[- _, + _]: Program
](using message: (Z && Y) => String)
  : ConvertibleToWritable[(Z && Y), StdOut, >-->] with

  override def convert: (Z && Y) >--> StdOut =
    object function {

      val convert: (Z && Y) => StdOut =
        (z, y) =>
          StdOut{ _ =>
            println(message(z, y))
          }

    }

    function.convert asProgram
```

Time for a first example

## `factorial`

```scala
package examples.specification.program

import psbp.external.specification.program.Program

import examples.specification.function.{ 
  isZero
  , one
  , subtractOne
  , multiply
}

def factorial[
  >-->[- _, + _]: Program
]: BigInt >--> BigInt =

  val program: Program[>-->] = 
    summon[Program[>-->]]
  import program.{
    Let
    , If
  }

  If(isZero) {
    one
  } Else {
    Let {
      subtractOne >--> factorial
    } In {
      multiply
    }
  }
```

`factorial` makes use of the followinng functions and corresponding programs

```scala
package examples.specification.function

import scala.language.postfixOps

import psbp.external.specification.types.&&

import psbp.external.specification.function.Function

object function {

  val isZero: BigInt => Boolean =
    n =>
      n == 0
  
  def one[Z]: Z => BigInt =
    _ =>
      1
  
  val subtractOne: BigInt => BigInt =
    n =>
      n - 1
    
  val multiply: BigInt && BigInt => BigInt =
    (n, m) =>
      n * m

  // ...    
    
}

def isZero[
  >-->[- _, + _]: Function
]: BigInt >--> Boolean = 
  function.isZero asProgram
  
def one[
  Z
  , >-->[- _, + _]: Function
]: Z >--> BigInt =
  function.one asProgram

def subtractOne[
  >-->[- _, + _]: Function
]: BigInt >--> BigInt =
  function.subtractOne asProgram

def multiply[
  >-->[- _, + _]: Function
]: (BigInt && BigInt) >--> BigInt =  
  function.multiply asProgram

// ...  
```

We also need a `BigInt` argument from `StdIn` and a conversion from the argument and the result of `factorial` to `StdOut`.

```scala
package psbp.external.implementation.stdIn

val stdInBigIntArgument: StdIn[BigInt] =
  StdIn(effect = { _ => BigInt(scala.io.StdIn.readInt) })
```

```scala
package examples.implementation.stdOut.writing.givens

import psbp.external.specification.types.&&

import psbp.external.specification.program.Program

import psbp.external.specification.writing.ConvertibleToWritable

import psbp.external.implementation.stdOut.StdOut

// givens

import psbp.external.implementation.stdOut.givens.argumentAndResultConvertibleToStdOutWritable

given ((BigInt && BigInt) => String) = 
  (i, j) => 
    s"applying factorial to argument $i yields result $j"

given factorialArgumentAndResultConvertibleToStdOutWritable[
  >-->[- _, + _]: Program 
]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
  argumentAndResultConvertibleToStdOutWritable
```

## `materializedMainFactorial`

We are now ready for `materializedMainFactorial`

```scala
package examples.implementation.stdOut.writing.bigInt.reading

import psbp.external.specification.program.Program

import psbp.external.specification.program.main.toMain

import psbp.external.specification.reading.Reading

import psbp.external.specification.writing.Writing

import psbp.external.specification.materialization.Materialization

import psbp.external.implementation.stdOut.StdOut

import examples.specification.program.factorial

// givens

import psbp.external.specification.program.reading.givens.productionFromReading

import psbp.external.specification.program.writing.givens.consumptionFromConvertibleToWritable

import psbp.external.implementation.stdOut.givens.stdOutWritable

import examples.implementation.stdOut.writing.givens.factorialArgumentAndResultConvertibleToStdOutWritable

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
```

## `mainFactorial`

Finally we can define `mainFactorial`.

```scala
package examples.implementation.active.program.stdOut.writing.stdIn.reading

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

// givens

import psbp.external.implementation.stdOut.givens.stdOutWritable

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingProgram

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingReading

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingWriting

import psbp.external.implementation.active.writing.reading.givens.activeWritingReadingMaterialization

import examples.implementation.stdIn.reading.givens.stdInBigIntArgument

@main def mainFactorial(args: String*): Unit =

  import examples.implementation.unit.givens.pleaseTypeAnInteger

  val (stdOut, (_, _)) = materializedMainFactorial

  val effect = stdOut.effect(())
```

`mainFactorial` needs `given pleaseTypeAnInteger` of type `Unit` to read from standard input.

```scala
package examples.implementation.unit.givens

given pleaseTypeAnInteger: Unit = 
  println("Please type an integer")
```

Moreover the `effect` of `StdOut` needs to be performed to write to standard output.

```
```