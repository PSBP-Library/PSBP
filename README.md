# The essence of programming

There are a lot of problem domains we have few knowledge about.

There are also a few problem domains we have lot of knowledge about.

But what is the *essence* of a specific problem domain?

Designing a *domain specific language*, a.k.a. DSL, for that specific problem domain is one way to reveal the essence of that specific problem domain.

Think of writing a domain specific language library as teaching about the essence of a specific problem domain to a programming language compiler.

*Programming* is a problem domain like any other one, so it makes sense to write a domain specific language library for it in order to reveal the essence of it.

The course explains the design of a domain specific language `Scala` library for the programming problem domain.

The *essence of programming* is, unsurprisingly, captured in what, in this course, is referred as a *program*.

The name of the `Scala` library is `PLP`, standing for, *program level programming*.

Programs of type `Z >--> Y` are *specifications* of *generalizations of functions* of type `Z => Y`.

On the one hand, program level programming is a generalization of *pure function level programming*.

On the other hand, program level programming, allows for specifying *program capabilities* that go beyond *pure function capabilities*. 

Program specifications can be *extended* to specify *effects*.

Effects can be *external*, like interaction with a file system, or *internal*, like randomness.

Programs are part of *main programs* of type `Unit >--> Unit`.

Programs are, eventually, *implemented* as functions.

Implemented main programs are *materialized* to functions.

The types of those functions reflect the specified effects that the programs that are part of the main programs can make use of.

`@main` `Scala` code makes use of materialized implemented main programs.

Recall that effects are specified.

Side effects are materialized implemented effects.

The decision of what those side effects are is pushed to the boundaries of `@main` `Scala` code.

Internally, the `Scala` library is also a domain specific language library for the *computing* problem domain.

Computations of type `C[Y]` are *specifications* of *generalizations of expressions* of type `Y`.

Recall that programs of type `Z >--> Y` are specifications of generalizations of functions of type `Z => Y`.

Functions are implemented using expressions.

Program capabilities can be implemented using computation capabilities.

More precisely, functions of type `Z => C[Y]` are programs.

Functions resp. programs are *denotational* artifacts. 

At *development time* they are *definitions* resp. *declarations* of *transformations* of an *argument* to a *result*.

Expressions resp. materialized implemented computations are *operational* artifacts.

At *runtime* they are *evaluated* resp. *executed* to a *result*.

The external programming DSL is a programming interface for *application* developers.

The internal computing DSL is a programming interface for *library* developers.

Specifying things is done by *classifying* them in *classes*, also known as *categorizing* them in *categories*.

The specifications of the `PLP` library are `trait`'s.

They define *higher kinded type* classes.

More precisely *binary type constructor classes*, like `trait Program[>-->[- _, + _]]` for programs and *unary type constructor classes*, like `trait Computation[C[+ _]]` for computations.

The *variance annotations*, `-` for *contravariance* resp. `+` for *covariance*, specify the *require less* resp. *provide more* programming principle.

Implementing a classified class is done by *giving* an instance of the class.

The implementations of the `PLP` library are `given`'s.

They are *higher kinded type* instances.

Ok, enough vocabulary, let's start.

## `Classification`

`Classification` is based on `Identity` and `Composition`.

```scala
package plp.external.specification.classification

import plp.external.specification.identity.Identity

import plp.external.specification.composition.Composition

trait Classification[>-->[- _, + _]]
  extends Identity[>-->]
  , Composition[>-->]
```

`Classification` simply combines `Identity` and `Composition`.

## `Identity`

```scala
package plp.external.specification.identity

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

`Identity` specifies, using `identity`, a transformation that *does nothing*. 

Its result is equal to its argument.

On its own, `identity` is not useful, but it turns out to be useful as *program component* of *composite programs*.

`identity` is a *generic* program.

It is convenient to define a handful of *typeful names* like  `` `z>-->z` ``.

They turn out to be suggestive when used as program component of composite generic programs.

`` `u>-->u` `` is not generic, it is reserved for the `Unit` type.

## `Composition`

```scala
package plp.external.specification.composition

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

`Composition` specifies, using `andThen` and corresponding infix `extension` `>-->`, that programs can be *composed sequentially*.

Note that the second parameter of `andThen` is a *by name* one.

Note that `` `z>-->y` >--> `y>-->x` `` has type `Z >--> X`, as suggested by the typeful names.

## `Function`

```scala
package plp.external.specification.function

trait Function[>-->[- _, + _]]: 

  // declared

  def toProgram[Z, Y]: (Z => Y) => (Z >--> Y)

  // defined

  extension [Z, Y] (`z=>y`: Z => Y) 
    def asProgram: Z >--> Y =
      toProgram(`z=>y`)
```

`Function` specifies, using `toProgram` and corresponding postfix `extension` `asProgram`, that *pure functions* of type `Z => Y` can be used *as programs* of type `Z >--> Y`.

## `Construction`

```scala
package plp.external.specification.construction

import plp.external.specification.types.&&

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
package plp.external.specification.types

// product

type &&[+Z, +Y] = (Z, Y)

// ...
```

`Construction` specifies, using `construct` and corresponding infix `extension` `&&`, that programs can *construct product based values*.

Note that the second parameter of `construct` is a *by name* one.

## `Condition`

```scala
package plp.external.specification.condition

import plp.external.specification.types.||

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
package plp.external.specification.types

// ...

// sum

enum ||[+Z, +Y]:
  case Left(z: Z) extends (Z || Y)
  case Right(y: Y) extends (Z || Y)

// ...  
```

`Condition` specifies, using `conditionally` and corresponding infix `extension` `||`, that programs can *conditionally perform sum based logic*.

Note that the both parameters of `construct` are *by name* ones.

## Utilities

Many definitions use *auxiliary pure functions* and corresponding programs like *projections out of products* and *injections into sums*.

```scala
package plp.external.specification.function

import scala.language.postfixOps

import plp.external.specification.types.{ 
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

Many more auxiliary pure functions and corresponding programs are used, for example

```scala
package plp.external.specification.function

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

`` `(z&&b)=>(z||z)` ``  decides to let `z` go `Left` or to let `z` go `Right` based upon a `Boolean` being `true` or `false`.

## `Program`

```scala
package plp.external.specification.program

import scala.language.postfixOps

import plp.external.specification.types.{ 
  &&
  , || 
}

import plp.external.specification.classification.Classification

import plp.external.specification.function.Function

import plp.external.specification.construction.Construction

import plp.external.specification.condition.Condition

import plp.external.specification.function.{ 
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

`&&&` is a defined infix `extension` that is more complex versions of the declared `extension` `&&`.

`|||` is a defined infix `extension` that is more complex versions of the declared `extension` `||`.

`Let { ... } In { ... }` defines how programs can use *local values*.

`If() { ... } Else { ... }` defines how programs can use *if-then-else logic*.

Note that both `Let` and `In` are defined in a somwwhat special way to be able to use them in a a *fluent* way.

`Scala`, being a *sca*lable *la*nguage, shines here!

We need a few extra auxiliary pure functions and corresponding programs.

This is a recurring theme, but, eventually, we will have enough of them.

```scala
package plp.external.specification.function

// ...

// construction

// ...

def `z=>(z&&z)`[Z]: Z => (Z && Z) =
  z =>
    (z, z)   

def `(z&&y&&x)=>(y&&x)`[Z, Y, X]: (Z && Y && X) => (Y && X) =
  case ((_, y), x) =>
    (y, x)

// ...

// condition

// ...

def `(z||z)=>z`[Z]: (Z || Z) => Z =
  foldSum(z => z, z => z)  
  
def `(y||x)=>b`[Y, X]: (Y || X) => Boolean =
  foldSum(_ => true, _ => false)

def `(y||x)=>y`[Y, X]: (Y || X) => Y =
  foldSum(y => y, _ => ???) 

def `(y||x)=>x`[Y, X]: (Y || X) => X =
  foldSum(_ => ???, x => x) 

// ...

// construction

// ...

def `z>-->(z&&z)`[
  Z
  , >-->[- _, + _]: Function
]: Z >--> (Z && Z) =
  `z=>(z&&z)` asProgram  

def `(z&&y&&x)>-->(y&&x)`[
  Z, Y, X  
  , >-->[- _, + _]: Function
]: (Z && Y && X) >--> (Y && X) =
  `(z&&y&&x)=>(y&&x)` asProgram

// ...

// condition

// ...

  Z
  , >-->[- _, + _]: Function
]: (Z || Z) >--> Z =
  `(z||z)=>z` asProgram  
  
def `(y||x)>-->b`[
  Y, X
  , >-->[- _, + _]: Function
]: (Y || X) >--> Boolean =
  `(y||x)=>b` asProgram

def `(y||x)>-->y`[
  Y, X
  , >-->[- _, + _]: Function
]: (Y || X) >--> Y =
  `(y||x)=>y` asProgram

def `(y||x)>-->x`[
  Y, X
  , >-->[- _, + _]: Function
]: (Y || X) >--> X =
  `(y||x)=>x` asProgram  

```

## Exercise: power of expression

It turns that `&&&` and `Let { ... } In { ... }` have the same power of expression as product based value construction.

It turns that `|||` and `If( ... ) { ... } Else { ... }` have the same power of expression as sum based conditional logic.

Below are the programs that, in a way, can be seen as *constructive proofs* of the same power of expression statements above.

Note that they are proofs in the world of programs (specifications of generalizations of functions).

```scala
package exercises

import plp.external.specification.types.{
  &&
  , ||
}  

import plp.external.specification.function.{ 
  `z>-->(z&&z)`
  , `(z&&y)>-->z` 
  , `(z&&y&&x)>-->(y&&x)` 
  , `(z||z)>-->z`
  , `(y||x)>-->b`
  , `(y||x)>-->y`
  , `(y||x)>-->x`
}

import plp.external.specification.program.Program

def `construct using &&&`[
  Z, Y, X
  , >-->[- _, + _]: Program
](
  `z>-->y`: Z >--> Y
  , `z>-->x`: => Z >--> X
): Z >--> (Y && X) =
  `z>-->(z&&z)` >--> (`z>-->y` &&& `z>-->x`)

def `construct using Let`[
  Z, Y, X
  , >-->[- _, + _]: Program
](
  `z>-->y`: Z >--> Y
  , `z>-->x`: => Z >--> X
): Z >--> (Y && X) =
 
  val program: Program[>-->] = 
    summon[Program[>-->]]
  import program.Let

  Let {
    `z>-->y`
  } In {
    Let {
      `(z&&y)>-->z` >--> `z>-->x`
    } In {
      `(z&&y&&x)>-->(y&&x)`
    }
  }

def `conditionally using |||`[
  Z, Y, X
  , >-->[- _, + _]: Program
](
  `y>-->z`: => Y >--> Z
  , `x>-->z`: => X >--> Z
): (Y || X) >--> Z =
  (`y>-->z` ||| `x>-->z`) >--> `(z||z)>-->z`

def `conditionally using If`[
  Z, Y, X
  ,  >-->[- _, + _]: Program
](
  `y>-->z`: => Y >--> Z
  , `x>-->z`: => X >--> Z
): (Y || X) >--> Z =

  val program: Program[>-->] = 
    summon[Program[>-->]]
  import program.If

  If(`(y||x)>-->b`) {
    `(y||x)>-->y` >--> `y>-->z`
  } Else {
    `(y||x)>-->x` >--> `x>-->z`
  }

```

You may argue that program level programming using auxiliary programs like `` `(z&&y&&x)>-->(y&&x)` `` to explicitly project values out of products of an argument together with local values is more trouble than it's worth.

Program level programming promotes writing many small programs and combining them using program capabilities to larger composite ones.

As such the size of products of an argument together with local values stays under control.

Good programmers write *baby code anyway.

## `Production` and `Consumption`

Programming is not only about *pure functions*, *transforming arguments to results*, *constructing values* and *performing conditional logic*.

As stated before, also *impure* capabilities are part of the programming world.

Two important such capabilities are *production of values*, for example from *standard input*, and *consumption of values*, for example to *standard output*.

```scala
package plp.external.specification.production

trait Production[>-->[- _, + _], +Z]:

  // declared

  private[plp] def produce: Unit >--> Z

  // defined

  private[plp] def `u>-->z`: Unit >--> Z =
    produce
```

`Production` specifies, using `produce` and corresponding `` `u>-->z` ``, that programs can *produce values* (*out of the blue*, a.k.a. */dev/null*, so to speak).

```scala
package plp.external.specification.consumption

trait Consumption[-Y, >-->[- _, + _]]:

  // declared

  private[plp] def consume: Y >--> Unit

  // defined

  private[plp] def `y>-->u`: Y >--> Unit =
    consume
```

`Consumption` specifies, using `consume` and corresponding `` `y>-->u` ``, that programs can *consume values* (*into the blue*, a.k.a. */dev/null*, so to speak).

## `toMain`

A *main program* is program of type `Unit >--> Unit`.

Programs are *local*, *type-safe* artifacts.

Main programs are *remote* artifacts.

Main programs interact with each other using the *type-unsafe* remote outside world.

You may argue that the *synchronous* (a.k.a. *active*) outside world can be standardized to be type-safe, but standardizing the *asynchronous* (a.k.a. *reactive*) ouside world to be type-safe is another matter.

Once remoteness comes in, in its full glory, *standardizing* type-safeness across a variety of technologies becomes difficult.

Defining asynchronous (a.k.a. reactive) local artifacts in a type-safe way is easier.

Using `toMain` below, a program can be combined with production and consumption to obtain a *main program*.


```scala
package plp.external.specification.program.main

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.production.Production

import plp.external.specification.consumption.Consumption

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

From a produced argument of type `Z`, `Let { ... }` constructs, using `` `z>-->y` `` a local result value of type `Y` that, together with the argument, is available for consumption in `In { ... }` using `` `(z&&y)>-->u` ``.

When using `>-->` instead of `Let { ... } In { ... }` the produced argument would not be available for consumption.

The combination of `Composition` and `Construction` is necessary for something simple as consuming a produced argument when transformed.

## `Materialization`

```scala
package plp.external.specification.materialization

trait Materialization[>-->[- _, + _], -Z, +Y]:

  // declared

  val materialize: (Unit >--> Unit) => Z ?=> Y
```

Main programs are *materialized* to functions of type `Z ?=> Y`.

When `given` an argument of type `Z`, a function of type `Z ?=> Y` is, from a type system point of view, essentially the same as its result of type `Y`.

As stated before, the types `Z` and `Y` reflect the impure capabilities of `>-->[- _, + _]`.

## `given`'s

Program specifications are *implemented* as `given`'s and made available using *dependency injection* `import`, especially in in `@main` code that uses *materialized implemented main programs*.

## `Reading` and `Writing`

Production and consumption are related to *reading* and *writing*.

Production resp. consumption happen at the beginning resp. end of a main program.

Reading and writing can also happen in the middle of a program.

Note that `produce` and `consume` are not public, they are only used in the definition of `toMain`.

```scala
package plp.external.specification.reading

trait Reading[R, >-->[- _, + _]]:

  // declared

  def read: Unit >--> R

  // defined

  def `u>-->r`: Unit >--> R =
    read
```

`Reading` specifies, using `read` and corresponding `` `u>-->r` ``,  that programs can *read values*.

```scala
package plp.external.specification.reading

import plp.external.specification.production.Production

import plp.external.specification.reading.Reading

given [
  Z: [Z] =>> Reading[Z, >-->]
  , >-->[- _, + _]  
]: Production[>-->, Z] with
    
  val reading = summon[Reading[Z, >-->]]
  import reading.read
  
  override private[plp] def produce: Unit >--> Z =
    read
```

The definition of the `given` is completely trivial.

Although it is a *generic* `given`, is only `import`ed by *type*, so that it does not need to have a name.

## `Writable`

A *writable* is something that *can be written*.

```scala
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
```

`Writable` specifies, using `nothing`, that *nothing has been written*, and using `append`, and corresponding infix `extension` `+` that *written things can be appended*.

## `ConvertibleToWritable`

```scala
package plp.external.specification.writing

trait ConvertibleToWritable[
  -Y
  , W: Writable, 
  >-->[- _, + _]
]:

  // declared

  private[plp] def convert: Y >--> W

  // defined
  
  private[plp] def `y>-->w`: Y >--> W =
    convert
```

`ConvertibleToWritable` specifies, using `convert` and corresponding `` `y>-->w` ``, that programs can *convert values to something that can be written*.

## `Writing`

```scala
package plp.external.specification.writing

trait Writing[W: Writable, >-->[- _, + _]]:

  // declared
  
  def write: W >--> Unit

  // defined

  def `w>-->u`: W >--> Unit =
    write
```

`Writing` specifies, using `write` and corresponding `` `w>-->u` ``, that programs can *write things that can be written*.

```scala
package plp.external.specification.program.writing

import plp.external.specification.consumption.Consumption

import plp.external.specification.program.Program

import plp.external.specification.writing.{
  Writable
  , ConvertibleToWritable
  , Writing
}

given [
  Y: [Y] =>> ConvertibleToWritable[Y, W, >-->]
  , W: Writable
     : [W] =>> Writing[W, >-->]
  , >-->[- _, + _]: Program 
]: Consumption[Y, >-->] with 

  private val convertibleToWritable = summon[ConvertibleToWritable[Y, W, >-->]]
  import convertibleToWritable.`y>-->w`
  
  private val writing = summon[Writing[W, >-->]]
  import writing.`w>-->u`

  override private[plp] def consume: Y >--> Unit =
    `y>-->w` >--> `w>-->u`
```

The definition of the `given` is a bit more involved.

To mention one thing, it requires sequential composition.

Although it is a generic `given`, is only `import`ed by type, so that it does not need to have a name.

Note the usage of typeful names in `` `y>-->w` >--> `w>-->u` ``, hopefully suggesting a progam of type `Y >--> Unit`.

## `Computation`

The `PLP` library has an internal part, *specifying* what *computations* are all about.

Again, think of computations as generalizations of *expressions* and of *executing* computations as a generalization of *evaluating* expressions.

```scala
package plp.internal.specification.computation

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.binding.Binding

private[psbp] trait Computation[C[+ _]] 
  extends Resulting[C] 
  with Binding[C]
```

`Computation` simply combines `Resulting` and `Binding`.

A *computation* has type `C[Y]` for a *unary type constructor* `C[+ _]` that is *covariant* in its *type paramenter*.

## `Resulting`

```scala
package plp.internal.specification.resulting

private[psbp] trait Resulting[C[+ _]]:

  // declared

  private[psbp] def result[Z]: Z => C[Z]
```

`Resulting` specifies, using `result`, a *pure computation* that does nothing.

Its *result* is equal to argument of `result`.

## `Binding`

```scala
package plp.internal.specification.binding

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

`Binding` specifies, using `bind`, that the result of executing an *inner computation* can be *bound* to a *continuation* that turns it into an *outer computation* to continue execution with. 

## `ProgramFromComputation`

```scala
package plp.external.implementation.computation

private[psbp] type ProgramFromComputation[C[+ _]] = [Z, Y] =>> Z => C[Y]
```

`ProgramFromComputation` is a just convenient type synonym.

## `foldSum`

```scala
package plp.external.specification.function

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
package plp.external.implementation.computation

import plp.external.specification.types.{ 
  &&
  , ||
}

import plp.external.specification.function.foldSum

import plp.external.specification.program.Program

import plp.internal.specification.computation.Computation

private[plp] given givenProgramFromComputation[
  C[+ _]: Computation
]: Program[ProgramFromComputation[C]] with
  
  private val computation = 
    summon[Computation[C]]
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

Note that the `given` has name `givenProgramFromComputation`.

`givenProgramFromComputation` is a generic `given` that is used to define *specific* `given`'s where it is `import`ed by *name*.

Specific `given`'s, and generic `givens` that are only `import`ed by type do need to have a name.

## `NaturalTransformation`

How do we go from the *pure function level programming world* to the *program level programing world* that also has *specified impure programming capabilities*, referred to as *effects*.

This is where *transformations* enter the picture.

```scala
package plp.internal.specification.naturalTransformation

private[psbp] trait ~>[-F[+ _], +T[+ _]]:

  // declared

  private[psbp] def apply[Z]: F[Z] => T[Z]
```

A *natural transformation* has type `F ~> T` for a *binary, unary type constructor argument, type constructor* `~>[-F[+ _], +T[+ _]]`.

This is a whole mouth full, but do not worry, it is just a way to deal with the higher-kindedness of type classification.

Think of a natural transformation as a *generic function* that works at the *unary type constructor* level instead of at the *type level*.

Think of natural transformations as changing *container types* rather than *container element types*.

## `ComputationTransformation`

Natural transformations are also used to change *computation types* much in the same way as they are used change container types.

By transforming a computation type `[Y] =>> C[Y]` so natural transformations *enrich* the corresponding program type `[Z, Y] =>> ProgramFromComputation[C][Z, Y]` with extra program capabilities. 

```scala
package plp.internal.specification.computation.transformation

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.naturalTransformation.~>
  
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

Note the, hopefully, suggestive usage of typeful names, this time for functions.

## Exercise: define `result` and `join` as natural transformations

Both `result` can be defined as a natural transformation.


```scala
package plp.exercises

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.naturalTransformation.~>

private[plp] def resultAsNaturalTransformation[C[+ _]: Resulting]: ([Z] =>> Z) ~> C =
  val resulting: Resulting[C] = summon[Resulting[C]]
  import resulting.result

  new {
    override def apply[Z]: Z => C[Z] =
      result
  }
```

Both `join` can be defined as a natural transformation.


```scala
package plp.exercises

import plp.internal.specification.binding.Binding
      
import plp.internal.specification.naturalTransformation.~>

private[plp] def joinAsNaturalTransformation[C[+ _]: Binding]: ([Z] =>> C[C[Z]]) ~> C =
  val binding: Binding[C] = summon[Binding[C]]
  import binding.join

  new {
    override def apply[Z]: C[C[Z]] => C[Z] =
      join
  } 
```

## exercise: define `bind` using `join` as natural transformation

**Hint** This exercise need an extra computation capability. 

```scala
package plp.exercises

import plp.internal.specification.binding.Binding

private[plp] trait Lifting[C[+ _]]:

  private[plp] def lift[Z, Y]: (Z => Y) => (C[Z] => C[Y])

private[plp] def bindUsingJoinAsNaturalTransformation[
  Z, Y
  , C[+ _]: Binding
          : Lifting
] (
  cz: C[Z]
  , `z=>cy`: => Z => C[Y]
): C[Y] =
  
  val lifting: Lifting[C] = summon[Lifting[C]]
  import lifting.lift

  joinAsNaturalTransformation apply lift(`z=>cy`)(cz)
```

## `ReadingTransformed`

```scala
package plp.internal.implementation.computation.transformation.reading

private[psbp] type ReadingTransformed[
  R
  , C[+ _]
] = [Y] =>> R ?=> C[Y] 
```

Transforming computations so that they can read values uses the type `ReadingTransformed[R, C]`.

Note that `R` is a *to be* `given` *argument type*.

## `givenReadingTransformedReading`

```scala
package plp.internal.implementation.computation.transformation.reading

import plp.external.specification.reading.Reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenReadingTransformedReading[
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

Defining `read` for `` `=>T` `` uses `summon[R]`, the to be `given` argument of type `R`, and transforms it to a computation using `result` of `F`.

Note that the `given` has name `givenReadingTransformedReading`.

`givenReadingTransformedReading` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

## `readingTransformedComputation`

```scala
package plp.internal.implementation.computation.transformation.reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.naturalTransformation.~>

import plp.internal.specification.computation.transformation.ComputationTransformation

private[plp] given givenReadingTransformedComputation[
  R
  , C[+ _]: Computation
]: ComputationTransformation[
  C
  , ReadingTransformed[R, C]
] with Computation[ReadingTransformed[R, C]] with

  private type F[+Y] = C[Y]
  private type T[+Y] = ReadingTransformed[R, C][Y]

  private type `=>T` = [Z, Y] =>> ProgramFromComputation[T][Z, Y]

  private val computation = summon[Computation[F]]
  import computation.{ 
    bind => bindF
  }

  override private[plp] val `f~>t`: F ~> T = 
    new {
      def apply[Z]: F[Z] => T[Z] =
        fz => 
          fz
    }  

  override private[plp] def bind[Z, Y](
    tz: T[Z]
    , `z=>ty`: => Z => T[Y]
  ): T[Y] =
    bindF(tz, z => `z=>ty`(z))
```

When defining `f~>t` and `bind`, the type system can take the to be `given` argument of type `R` into account to check type correctness.

Note that the `given` has name `givenReadingTransformedComputation`.

`givenReadingTransformedComputation` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

## `givenReadingTransformedMaterialization`

```scala
package plp.internal.implementation.computation.transformation.reading

import plp.external.specification.materialization.Materialization

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenReadingTransformedMaterialization[
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
            val y = materializeF(resultF)
            resultF(y)
      )
```

Defining `materialize` for `T` in terms of `materialize` of `F` is a bit more complex.

Defining a local value `y`, which the type system infers to have type `Y`, is not necessary but is instructive.

Note that the `given` has name `givenReadingTransformedMaterialization`.

`givenReadingTransformedMaterialization` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

## `WritingTransformed`

```scala
package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.types.&&

private[plp] type WritingTransformed[
  W
  , C[+ _]
] = [Y] =>> C[W && Y]
```

Transforming computations so that they can write things that can be written uses the type `WritingTransformed[W, C]`.

## `givenWritingTransformedWriting`

```scala
package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.writing.{
  Writable
  , Writing
}

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenWritingTransformedWriting[
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

Defining `write` for `` `=>T` `` tuples `w` with `()` to `(w, ())`, and turns it into a computation using `result` of `F`.

Note that the `given` has name `givenWritingTransformedWriting`.

`givenWritingTransformedWriting` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

## `givenWritingTransformedComputation`

```scala
package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.types.&&

import plp.external.specification.writing.Writable

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.naturalTransformation.~>

import plp.internal.specification.computation.transformation.ComputationTransformation

private[plp] given givenWritingTransformedComputation[
  W : Writable
  , C[+ _]: Computation
]: ComputationTransformation[
  C
  , WritingTransformed[W, C]
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

  override private[plp] val `f~>t`: F ~> T = new {
    def apply[Z]: F[Z] => T[Z] =
      fz =>
        bindF(fz, z => resultF((nothing, z)))
  }  

  override private[plp] def bind[Z, Y](
    tz: T[Z]
    , `z=>ty`: => Z => T[Y]
  ): T[Y] =
    bindF(tz, (w1, z) =>
      val (w2, y): W && Y = `z=>ty`(z)
      resultF(append(w1, w2), y)
    )
```

Defining `f~>t` uses `nothing`.

Defining `bind` uses `append`.

Note that the `given` has name `givenWritingTransformedComputation`.

`givenWritingTransformedComputation` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

## `givenWritingTransformedMaterialization`

```scala
package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.writing.Writable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

private[plp] given givenWritingTransformedMaterialization[
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
            val y = materializeF(resultF)
            resultF((w, y))
      )
```

Again, defining `materialize` for `T` in terms of `materialize` of `F` is a bit more complex.

Again, defining a local value `y`, which the type system infers to have type `Y`, is not necessary but is instructive.

Note that the `given` has name `givenWritingTransformedMaterialization`.

`givenWritingTransformedMaterialization` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

## `givenReadingTransformedWriting`

So what about combining reading and writing?

`ReadingTransformed` preserves `Writing`.

```scala
package plp.internal.implementation.computation.transformation.writing.reading

import plp.external.specification.reading.Reading

import plp.external.specification.writing.{
  Writable
  , Writing
}

import plp.internal.specification.computation.Computation

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.implementation.computation.transformation.reading.ReadingTransformed

private[plp] given givenReadingTransformedWriting[
  R
  , W: Writable
  , C[+ _]: [C[+ _]] =>> Writing[W, ProgramFromComputation[C]]
]: Writing[
  W
  , ProgramFromComputation[ReadingTransformed[R, C]]
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

When defining `write` for `` `=>T` ``, the type system can take the to be `given` argument of type `R` into account to check type correctness.

Note that the `given` has name `givenReadingTransformedWriting`.

`givenReadingTransformedWriting` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

All machinery is now available to define `given` implementations of the `Program`, `Reading` and `Writing`, and corresponding `Materialization` specification `trait`'s.

Let's start with `Program`, and corresponding `Materialization`.

## `Active`

```scala
package plp.external.implementation.active

import plp.external.implementation.computation.ProgramFromComputation

type Active = [Y] =>> Y

type `=>A`= [Z, Y] =>> ProgramFromComputation[Active][Z, Y]
```

Think of an *active computation* of type `Active[Y]` as an expression and of an *active program* of type `` Z `=>A` Y `` as a function.

## `activeComputation`

```scala
package plp.external.implementation.active

import plp.external.specification.program.Program

import plp.external.implementation.active.Active

import plp.internal.specification.computation.Computation

private[plp] given Computation[Active] with

  private[plp] def result[Z]: Z => Active[Z] =
    z =>
      z

  private[plp] def bind[Z, Y](
    cz: Active[Z]
    , `z=>cy`: => Z => Active[Y]
  ): Active[Y] =
    `z=>cy`(cz)
```

Defining `result` and `bind` is trivial.

## `activeMaterialization`

```scala
package plp.external.implementation.active

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.`=>A`

given Materialization[`=>A`, Unit, Unit] with
  val materialize: (Unit `=>A` Unit) => Unit ?=> Unit =
    `u>-->u` => 
      `u>-->u`(summon[Unit])
```

Defining `materialize` is trivial and uses `summon[Unit]`.

Let's add `Reading` and `Writing`, and corresponding `Materialization`.

## `ActiveReading`

```scala
package plp.external.implementation.active.reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.implementation.computation.transformation.reading.ReadingTransformed

import plp.external.implementation.active.Active

type ActiveReading[R] = [Y] =>> ReadingTransformed[R, Active][Y] 

type `=>AR`[R] = [Z, Y] =>> ProgramFromComputation[ActiveReading[R]][Z, Y]
```

## `ActiveWriting`

```scala
package plp.external.implementation.active.writing

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.implementation.computation.transformation.writing.WritingTransformed

import plp.external.implementation.active.Active

type ActiveWriting[W] = [Y] =>> WritingTransformed[W, Active][Y] 

type `=>AW`[W] = [Z, Y] =>> ProgramFromComputation[ActiveWriting[W]][Z, Y]
```

## `activeWritingWriting`

```scala
package plp.external.implementation.active.writing

import plp.external.specification.writing.{
  Writable
  , Writing
}

import plp.external.implementation.active.Active

import plp.external.implementation.active.writing.`=>AW`

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.{
  given Computation[Active]
}

import plp.internal.implementation.computation.transformation.writing.givenWritingTransformedWriting

given [W: Writable]: Writing[W, `=>AW`[W]] = 
  givenWritingTransformedWriting[W, Active]
```

The code above, and also some of the one below, consists of defining a new `given` in terms of existing `given`'s that are made available using dependency injection by `import`.

## `activeWritingComputation`

```scala
package plp.external.implementation.active.writing

import plp.external.specification.writing.Writable

import plp.external.implementation.active.Active

import plp.external.implementation.active.writing.ActiveWriting

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.{
  given Computation[Active]
}

import plp.internal.implementation.computation.transformation.writing.givenWritingTransformedComputation

private[plp] given [W: Writable]: Computation[ActiveWriting[W]] = 
  givenWritingTransformedComputation[W, Active]
```

## `activeWritingMaterialization`

```scala
package plp.external.implementation.active.writing

import plp.external.specification.writing.Writable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.Active

import plp.external.implementation.active.`=>A`

import plp.external.implementation.active.writing.`=>AW`

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.{
  given Computation[Active]
}

import plp.external.implementation.active.{
  given Materialization[`=>A`, Unit, Unit]
}

import plp.internal.implementation.computation.transformation.writing.givenWritingTransformedMaterialization

given [W: Writable]: Materialization[`=>AW`[W], Unit, (W, Unit)] =
  givenWritingTransformedMaterialization[W, Active, Unit, Unit]
```

## `ActiveWritingReading`

```scala
package plp.external.implementation.active.writing.reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.implementation.computation.transformation.reading.ReadingTransformed

import plp.external.implementation.active.writing.ActiveWriting

type ActiveWritingReading[W, R] = [Y] =>> ReadingTransformed[R, ActiveWriting[W]][Y]

type `=>AWR`[W, R] = [Z, Y] =>> ProgramFromComputation[ActiveWritingReading[W, R]][Z, Y]
```

## `activeWritingReadingReading`

```scala
package plp.external.implementation.active.writing.reading

import plp.external.specification.writing.Writable

import plp.external.specification.reading.Reading

import plp.external.implementation.active.writing.ActiveWriting

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
} 

import plp.internal.implementation.computation.transformation.reading.givenReadingTransformedReading

given [
  W: Writable
  , R
]: Reading[R, `=>AWR`[W, R]] = 
  givenReadingTransformedReading[R, ActiveWriting[W]]
```

## `activeWritingReadingWriting`

```scala
package plp.external.implementation.active.writing.reading

import plp.external.specification.writing.{
  Writable
  , Writing
}

import plp.external.implementation.active.writing.ActiveWriting

import plp.external.implementation.active.writing.`=>AW`

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.active.writing.{
  given Writing[?, `=>AW`[?]]
}

import plp.internal.implementation.computation.transformation.writing.reading.givenReadingTransformedWriting

given [
  W: Writable
  , R
]: Writing[W, `=>AWR`[W, R]] = 
  givenReadingTransformedWriting[R, W, ActiveWriting[W]]
```

## `activeWritingReadingProgram`

```scala
package plp.external.implementation.active.writing.reading

import plp.external.specification.program.Program

import plp.external.specification.writing.Writable

import plp.external.implementation.active.writing.ActiveWriting

import plp.external.implementation.active.writing.reading.{
  ActiveWritingReading
  , `=>AWR`
}

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
}

import plp.internal.implementation.computation.transformation.reading.givenReadingTransformedComputation

private[plp] given [
  W: Writable
  , R
]: Computation[ActiveWritingReading[W, R]] = 
  givenReadingTransformedComputation[R, ActiveWriting[W]]

import plp.external.implementation.computation.givenProgramFromComputation

given [
  W: Writable
  , R
]: Program[`=>AWR`[W, R]] = 
  givenProgramFromComputation[ActiveWritingReading[W, R]]
```

Note that, this time, we also define a `given` for `Program`.

## `activeWritingReadingMaterialization`

```scala
package plp.external.implementation.active.writing.reading

import plp.external.specification.writing.Writable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.ActiveWriting

import plp.external.implementation.active.writing.`=>AW`

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.active.writing.reading.ActiveWritingReading

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
}  

import plp.external.implementation.active.writing.{
  given Materialization[`=>AW`[?], Unit, (?, Unit)]
}

import plp.internal.implementation.computation.transformation.reading.givenReadingTransformedMaterialization

given [
  W: Writable
  , R
]: Materialization[
  `=>AWR`[W, R]
  , Unit
  , R ?=> (W, Unit)
] with 

  type `=>AWR[W,R]` = [Z, Y] =>> Z => ActiveWritingReading[W, R][Y]
    
  override val materialize: (Unit `=>AWR[W,R]` Unit) => Unit ?=> R ?=> (W, Unit) =        
    `u=>awru` => 
      givenReadingTransformedMaterialization.materialize(`u=>awru`) match {
        case (w, (_, u)) => (w, u)
      }
```

Note that the definition of `materialize` uses pattern matching to get rid of a nested tuple component of type `W` with value `nothing`.

## Programs as proofs

Note that, new `given`'s are defined in terms existing `given`'s .

In the last example, a new specific `given` for `Materialization` in terms existing specific `given`'s for `Computation`, `Materialization` and one generic `given` for `Materialization`.

Think of declaring a specification `trait` as stating a *theorem*.

Think of defining a `given` implementation of the specifcation as *proving the theorem*.

All proofs are *constructive* in the sense that they *prove existence using a definition*.

## `StdIn` and `StdOut`

We are not ready yet.

We need to define `given` implementations for `R` and `W`.

For now we use *standard input* to `read` `Z`'s from, and `Writable` *standard output* to ``convert` `Z && Y`'s to.

Reading from standard input is type specific and simple.

```scala
package plp.external.implementation.stdIn

case class StdIn[Z](effect: Unit => Z)
```

and, for example, for `BigInt`

```scala
package plp.external.implementation.stdIn

val stdInBigInt: StdIn[BigInt] =
  StdIn(effect = _ => BigInt(scala.io.StdIn.readInt))
```

Writing to standard output is type generic, and a bit more complex

```scala
package plp.external.implementation.stdOut

case class StdOut(effect: Unit => Unit)
```

```scala
package plp.external.implementation.stdOut

import plp.external.specification.types.&&

import plp.external.specification.writing.Writable

given Writable[StdOut] with

  // defined

  override private[plp] def nothing: StdOut = 
    StdOut(effect = identity )

  override private[plp] def append: (StdOut && StdOut) => StdOut =
    case (StdOut(firstEffect), StdOut(secondEffect)) =>
      StdOut(effect = firstEffect andThen secondEffect)
```

Any value of type `Z` can be converted to `StdInOut` as follows

```scala
package plp.external.implementation.stdOut

import scala.language.postfixOps

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.writing.{
  ConvertibleToWritable
}

import plp.external.implementation.stdOut.StdOut

given givenConvertibleToStdOut[
  Z: ToMessage
  , >-->[- _, + _]: Program
] : ConvertibleToWritable[Z, StdOut, >-->] with

  private val toMessage: ToMessage[Z] = summon[ToMessage[Z]]
  import toMessage.message

  override private[plp] def convert: Z >--> StdOut =

    object function {

      val convert: Z => StdOut =
        z =>
          StdOut{ _ =>
            println(message(z))
          }

    }

    function.convert asProgram
```

where

```scala
package plp.external.implementation.stdOut

trait ToMessage[Z]:
  val message: Z => String
```

converts the value of type `Z` to a message.

Time for a first example

## `factorial`

```scala
package examples.specification.program

import plp.external.specification.program.Program

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

`factorial` makes use of the following functions and corresponding programs

```scala
package examples.specification.function

import scala.language.postfixOps

import plp.external.specification.types.&&

import plp.external.specification.function.Function

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

## `materializedMainFactorial`

We are now ready for `materializedMainFactorial`

```scala
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
```

The `given ConvertibleToWritable` that is used by the code above is 

```scala
package examples.implementation.stdOut.writing.factorial

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.implementation.stdOut.StdOut

import plp.external.implementation.stdOut.ToMessage

import plp.external.implementation.stdOut.givenConvertibleToStdOut

given ToMessage[BigInt && BigInt] with 
  override val message: (BigInt && BigInt) => String =
    (i, j) => 
      s"applying factorial to argument $i yields result $j" 

given [
  >-->[- _, + _]: Program 
]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
  givenConvertibleToStdOut
```

## `mainFactorial`

Finally we can define `mainFactorial`.

```scala
package examples.implementation.active.program.stdOut.writing.stdIn.reading.factorial

import plp.external.specification.program.Program

import plp.external.specification.reading.Reading

import plp.external.specification.writing.Writable

import plp.external.specification.writing.Writing

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.stdOut.StdOut

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

import plp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}

import plp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
  , given Writing[StdOut, `=>AWR`[StdOut, BigInt]]
  , given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, Unit)]
}

@main def mainFactorial(args: String*): Unit =

  import examples.implementation.pleaseTypeAnInteger.{
    given Unit
  }
  
  import examples.implementation.stdIn.{
    given BigInt
  }

  import plp.external.implementation.stdOut.{ 
    given Writing[StdOut, ?]  
  }

  materializedMainFactorial
```

The `given Unit`, to start with, `given BigInt`, to read from standard input with, and `given Writing[StdOut, ?]`, to finish with by writin to standard output, are defined below

```scala
package examples.implementation.pleaseTypeAnInteger

given Unit = 
  println("Please type an integer")
```

and

```scala
package examples.implementation.stdIn

import plp.external.implementation.stdIn.stdInBigInt.effect

given BigInt = 
  effect(())
```

and

```scala
package plp.external.implementation.stdOut

import scala.language.postfixOps

import plp.external.specification.program.Program

import plp.external.specification.writing.Writing

given [>-->[- _, + _]: Program]: Writing[StdOut, >-->] with

  override def write: StdOut >--> Unit =

    object function {

      val write: StdOut => Unit =
        case StdOut(effect) =>
          effect(())
    }

    function.write asProgram  
```

It is also possible to define `verboseMainFactorial`, a more verbose version of `mainFactorial`

```scala
package examples.implementation.active.program.stdOut.writing.stdIn.reading.factorial

import plp.external.specification.program.Program

import plp.external.specification.reading.Reading

import plp.external.specification.writing.Writable

import plp.external.specification.writing.Writing

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.stdOut.StdOut

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

import plp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}

import plp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
  , given Writing[StdOut, `=>AWR`[StdOut, BigInt]]
  , given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, Unit)]
}
  
@main def verboseMainFactorial(args: String*): Unit =

  given Unit = println("Please type an integer")

  import plp.external.implementation.stdIn.stdInBigInt.effect

  given BigInt = effect(())

  val (stdOut, _) = materializedMainFactorial

  stdOut.effect(())
```



