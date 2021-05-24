# The essence of function-level programming

## Introduction

The introduction of this courses tries to set the scene of the core content of the course.

First of all, the course is illustrated with code, more precisely `Scala` code, even more precisely `Scala 3` code.

When dealing with code related concepts `Scala 3` is *implicit*, in other words, it is not explicitely mentioned any more.

## Problem domains

There are a lot of problem domains we have few knowledge about.

There are also a few problem domains we have lot of knowledge about.

But what is the *essence* of a problem domain?

## Domain specific language library (DSL) 

Writing a *domain specific language* library, in what folllows simply referred to as a DSL, for a specific problem domain is one way to reveal the essence of that problem domain.

Think of writing a DSL as teaching the essence of a problem domain to a *programming language compiler*.

## Specifications and implementations

Ideally libraries strictly separate *specifications* from *implementations*.

## Specifications

Specifying things is done by *classifying* them in *classes*, also known as *categorizing* them in *categories*.

We almost exclusively limit ourselves to specification `trait`'s that *declare* *higher kinded type* classes, typically *binary type constructor* classes and *unary type constructor* classes.

## Implementations

Implementing a specified class is done by giving a class *instance*.

We almost exclusively limit ourselves to implementation `given`'s that *define* higher kinded type class instances, typically binary type constructor class instances and unary type constructor class instances.

## Programming problem domain

The *programming problem domain* is a problem domain like any other one, so it makes sense to write a *DSL for programming* in order to reveal the *essence of programming*.

The course is based upon such a DSL, called `PLP`, standing for, *program-level programming*.

## Functional programming

Functional programming is about the mutual recursive relation between *functions* and *expressions*.

A function can be defined in terms of an expression using *abstraction*.

```scala
package introduction

def expression[Z, Y](unboundIdentifier: Z): Y = ??? 
```

```scala
package introduction

def functionInTermsOfExpression[Z, Y]: Z => Y = 
  val abstraction =
    (parameter: Z) => 
      expression(unboundIdentifier = parameter)
  abstraction 
```

A function has a *parameter* corresponding to the *unbound identifier* of an expression.

An expression can be defined in terms of a function using *binding*.

```scala
package introduction

def function[Z, Y]: Z => Y = parameter => ???
```

```scala
package introduction

def expressionInTermsOfFunction[Z, Y](parameter: Z): Y = 
  val expression =
    val argument = parameter
    val result = argument bind function
    result
  expression     
```

where

```scala
package introduction

extension [Z, Y] (parameter: Z) 
  def bind(function: Z => Y): Y =
    function apply parameter
```

An expression is obtained by binding an *argument* to the parameter of the function to obtain a *result*.

Functional programming is, perhaps, even more about the mutual recursive relation between *context functions* and *expressions*.

A context function can be defined in terms of an expression using an *expression context*

```scala
package introduction

def contextFunctionInTermsOfExpression[Z]: Z ?=> Nothing = 
  val expressionContext =
    val hole = summon[Z]
    expression(hole)
  expressionContext
```

An context function is an expression with a *hole* in it that *summons for a value*.

An expression can be defined in terms of a context function. 

```scala
package introduction

def contextFunction[Z]: Z ?=> Nothing = ???
```

```scala
package introduction

def expressionInTermsOfContextFunction[Z](value: Z): Nothing = 
  val expression =
    given hole: Z = value
    contextFunction
  expression
```

An expression is obtained by *giving a value* for the hole of the context function.

As for as the type system is concerned, context functions can be used as values

```scala
package introduction

object contextFunctionAsValue {
  def contextFunction[Z, Y]: Z ?=> Y = ??? 

  def value[Y]: Y = contextFunction
}
```

As for as the type system is concerned, values can be used as context functions

```scala
package introduction

object valueAsContextFunction {
  def value[Y]: Y = ???

  def contextFunction[Z, Y]: Z ?=> Y = value
}
```

## A complete context function example

Below are two context functions, `hello` and `goodbye`.

```scala
package introduction.contextFunctionExample

def hello: String ?=> String = 
  s"Hello, ${summon[String]}"

def goodbye: String ?=> String = 
  s"Goodbye, ${summon[String]}"
```

They are used by `printlnWithGreeting` to print a value with a greeting.

```scala
package introduction.contextFunctionExample

def printlnWithGreeting[Z](value: Z): String ?=> Unit = 
  val helloValue: String = hello
  val goodbyeValue: String = goodbye
  println(s"$helloValue\n$value\n$goodbyeValue")
```

Note that `hello` and `goodbye` are used as string values in `printlnWithGreeting`,

A `String` to be given for the hole of `hello` and `goodbye` can be defined as follows

```scala
package introduction.contextFunctionExample

given String = "World!"
```

And `printlnWithGreeting` can be used in `@main` code as follows.

```scala
package introduction.contextFunctionExample

import introduction.contextFunctionExample.{ given String }

@main def printlnWithGreetingMain(args: String*): Unit =
  
  case object contextFunctionExample

  printlnWithGreeting(contextFunctionExample)
```

Let's run it

```scala
[info] running introduction.contextFunctionExample.printlnWithGreetingMain 
Hello, World!
contextFunctionExample
Goodbye, World!
[success] 
```

Note that `printlnWithGreetingMain` uses dependency injection by `import` of the `given String`.

## The essence of functional programming

In 1992, in the *proceedings* of the 19th *Symposium on Principles of programming*, *Philip Wadler* published *The essence of functional programming*.

Expressions are generalized to *computations* to deal with *impure computation execution ingredients* that go beyond the *pure expression evaluation ingredients*.

After all, programming is not only about pure functions resp. context functions that

- *internally*, *always*, *succesfully*, *transform* an *argument* resp. *given value* to the *same* *result*

but also about such things as

- *state handling*, to, *internally*, *sometimes*, deal with *random* results,
- *failure handling*, to, *internally*, *sometimes*, deal with *unsuccessful* results,

and

- *input/output*, to, *externally*, deal with *program composition*.

and many more programming capabilities.

## Function-level programming

In 1977, in his *Turing Award* lecture *Can Programming be Liberated from the von Neumann Style?*, *John Backus* described `FP`, a *function-level* programming language.

The story goes that he designed `FP` as an excuse for `Fortran`, the programmng language for which he received the Turing Award.

Function-level programming restricts functional programming to *function-valued* expressions.

It makes use of the following pure capabilities

- a set of *primitive functions*

and

- function combinators

  - *composition*, functions can be *sequentially composed*
  - *construction*, functions can be used to *construct product based data*
  - *condition*, functions can be composed to *perform conditional sum based logic*

and

- sequencing

  -- functions can *aggregate sequences of values to results*

In 1977, long before 1992, `FP` did not deal with any impure capabilities at all.

## The essence of function-level programming
 
This course generalizes function-level programming to *program-level* programming.

Recall that expressions are generalized to computations to deal with impure capabilities.

Much in the same way context functions are generalized to *programs* to deal with impure capabilities.

Recall that context functions can be seen as expression contexts.

Much in the same way programs can be seen as *computation contexts*.

Note that we simply refer to the generalization of a context function as a program (not a context gprgram). 

So how does this course establish this generalization?

Among others by changing from a programming *language* that *does not* allow for *different implementations* of its *specification* and is *not* *extendible*, to a programming *library* that *does* allow for *different implementations* of its *specifications* and *is* *extendible*.

## Binary program type constructors and unary computation type constructors

The `PLP` library has an *external specification* part with corresponding *external implementations*, and an *internal specification* part with corresponding *internal implementations*.

The external specification part is a *DSL for programming*.

*Binary program type constructors* `>-->[- _, + _]` are specified as `trait`'s, referred to as program `trait`'s.

The members of program `trait`'s are referred to as *program capabilities*.

The internal specification part is a *DSL for computing*.

*Unary computation type constructors* `C[+ _]` are specified as `trait`'s, referred to as computation `trait`'s.

The members of computation `trait`'s are referred to as *computation execution ingredients*.

The *variance annotations*, `-` for *contravariance* resp. `+` for *covariance*, specify the *require less*, for arguments, resp. *provide more*, for results, programming principle.

## Programs and computations

Objects of type `Z >--> Y, defined in terms of program capabilities, are, somewhat abusively, referred to as *programs*.

They are *specifications of generalizations of functions* of type `Z => Y`.

The external programming DSL of the `PLP` library is a programming interface for *application* developers.

Objects of type `C[Y]`, defined in terms of computation execution ingredients, are, somewhat abusively, referred to as *computations*. 

They are *specifications of generalizations of expressions* of type `Y`.

The internal, `private[plp]`, computing DSL of the `PLP` library is a programming interface for *library* developers.

## Denotational (context) functions and operational expressions

It is intructive to think of a function resp. context function as a *denotational* artifact.

At *development time* it is a *definition* of how to *tranform* an *argument* resp. `given` to a *result*.

It is intructive to think of an expression as an *operational* artifact.

At runtime an expression is *evaluated* to a *result*.

You can think a function `` `z=>y` `` of type `Z => Y`, resp. context function `` `z?=>y` `` of type `Z ?=> Y`, as an *expression template* or *expression context*, an expression with a *hole* of type `Z` in it.

If an argument `z` of type `Z` resp. a `given` `z` of type `Z` is given for the hole of type `Z` in the expression context, then the expression context becomes an expression.

The result of evaluating that expression at runtime is the result of transforming argument `z`, resp `given` `z` using `` `z?=>y` ``, resp.  `` `z?=>y` ``.

## Programs and computations revisited

Recall that programs are specifications of generalizations of functions.

Recall that computations are specifications of generalizations of expressions.

Also recall that you can think of a context function as an expression context.

Program capabilities of binary program type constructors `[Z, Y] =>> Z ?=> C[Y]` can be defined in terms of the declared computation execution ingredients of unary computations type constructors `[Y] =>> C[Y]`.

In other words `given [C[+ _]: Computation]: Program[[Z, Y] =>> Z ?=> C[Y]]`, which can be read as *if* `C[Y]` *is a computations, then* `Z ?=> C[Y]` *is a program*.

## Denotational programs and operational computations

It is intructive to think of a program as a *denotational* artifact.

At *development time* it is a *declaration* of, among others, how to *tranform* a `given` to a *result*.

It is intructive to think of an computations as an *operational* artifact.

At runtime a computation is *executed* to a *result*.

You can think a program `` `z?=>cy` `` of type `Z ?=> C[Y]` as an *computation template* or *computation context*, an expression with a *hole* of type `Z` in it.

If a `given` `z` of type `Z` is given for the hole of type `Z` in the computation context, then the computation context becomes a computation.

The result of executing that computation at runtime is also referred to as the result of *running* program `` `z?=>cy` ``, given `z`.

## A teaser

How do we transform the world of expressions of type `Z` to the world of computations of type `C[Z]`?

How do programs of type `Z ?=> C[Y]` and of type  `Y ?=> C[Z]` compose?

It is not possible to give a result of type `C[Y]` as a `given` `y` of type `Y`.

## Effects

Function capabilities are *pure*, functions *transform arguments* to results*.

Program-level programming, allows for specifying *impure program capabilities* that go beyond *pure function capabilities*. 

More precisely, it is possible to `extend` program specification `trait`'s with `trait`'s that specify *effects*.

## Main programs

Programs of type `Unit >--> Z` can be combined with programs of type `Z >--> Y` and programs of type `(Z && Y) >--> Unit` to *main programs* of type `Unit >--> Unit`.

Programs of type `Unit >--> Z` resp. `(Z && Y) >--> Unit` make use of specified input effects resp. specified output effects.

## Materialization

Program types are *implemented* as function types.

Implemented main programs are *materialized* to functions.

The types of those functions reflect the declared effects in terms of which the main programs and the programs in terms of which they are defined, are defined.

Implemented effects can be *external*, for example, dealing with external *input/output*, or *internal*, for example, to dealing with internal *state*.

## Side effects

When *running materialized implemented main program*, side effects happen.

An implemented program, *somehow*, *transforms* an *argument*, if any, to a *result*, if any, making use of the defined pure programming capabilities in terms of which its specification is declared, while *performing* the defined effects in terms of which its specification is *declared*. 

A performed defined effect is referred to as a side effect.

For example, internal side effects, like generating a random number or external side effects like performing input/output.

Note that running a materialized implemented main program that makes use of a program that specifies to internally generate a random number, will always generate the same random number. 

Programs can also be implemened for testing purposes.

For testing purposes, typically, *no external side effects happen* because they require user interaction, before, during or after running materialized implemented main programs.

## Remarks

This subsection contains code that is not explained yet.

Do not concentrate on the details of the code, only on the explanation of the code.

Eventually, `@main` `Scala` code makes use of *materialized implemented main programs*.

For example

```scala
// ...

import plp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
  , given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, Unit)]
}

import examples.implementation.pleaseTypeAnInteger.{
  given Unit
}

import examples.implementation.stdIn.{
  given BigInt
}

import examples.implementation.stdOut.writing.factorial.{
  given ConvertibleToWritable[(BigInt && BigInt), StdOut, ?]
}

import plp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}

// ...

@main def mainFactorial(args: String*): Unit =

  materializedMainFactorial
```

Let's run the `@main` `Scala` code

```scala
[info] running examples.implementation.active.program.stdOut.writing.stdIn.reading.factorial.mainFactorial 
Please type an integer
10
applying factorial to argument 10 yields result 3628800
```

Below is an explanation.

Again, feel free to completely ignore it.

```scala
import plp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
}

import plp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}
```

Those `given`'s choose for *active programming*, somehow *reading* a *big integer* and somehow making use of *writable standard output*.

The adjective *active* is used as because, later, *reactive programming* will enter the picture as well.

```scala
import examples.implementation.stdIn.{
  given BigInt
}

import examples.implementation.stdOut.writing.factorial.{
  given ConvertibleToWritable[(BigInt && BigInt), StdOut, ?]
}
```

Those `given`'s choose to read the big integer argument from *standard input* and how to *convert* the big integer argument and big integer result to writable standard output.

```scala
import plp.external.implementation.active.writing.reading.{
  given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, Unit)]
}
```
This `given` chooses to *materialize* the implemented main program to a function of type `Unit ?=> (BigInt ?=> (StdOut, Unit))`.

```scala
import examples.implementation.pleaseTypeAnInteger.{
  given Unit
}
```

The important thing to note is that those `given`'s are *pushed to the boundaries of the* `@main` *code* by making use of *dependency injection* of `given` implementations *by* `import`.

This allows for maximum flexibility with minimum complexity. 



This `given` chooses for writing `Please type an integer` to standard output so we are left with function of type `BigInt ?=> (StdOut, Unit)`.

The `given BigInt` above from standard input turns the function into a value of type `(StdOut, Unit)`.

The `given Writable[StdOut]` above then writes the converted big integer argument `10` and big integer result `3628800` to standard output as `applying factorial to argument 10 yields result 3628800`.


We could have replaced the `given`s  explicitly by their definitions 

```scala
@main def verboseMainFactorial(args: String*): Unit =

  given Unit = println("Please type an integer")

  import plp.external.implementation.stdIn.stdInBigInt

  given BigInt = stdInBigInt.effect(())

  import plp.external.implementation.stdOut.givenConvertibleToStdOut

  given ToString[BigInt && BigInt] with 
    override val _toString: (BigInt && BigInt) => String =
      (i, j) => 
        s"applying factorial to argument $i yields result $j" 

  given [
    >-->[- _, + _]: Program 
  ]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
    givenConvertibleToStdOut

  val (stdOut, _) = materializedMainFactorial

  stdOut.effect(())
```

Note that reading from standard input resp. writing to standard output is done by performing a specified effect using `stdInBigInt.effect(())` resp. `stdOut.effect(())`.

For those who are curious, `materializedMainFactorial` is defined as follows

```scala
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
```

And, finally, for those who are even more curious, `factorial` is defined as follows

```scala
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

It makes use of 

It also makes use of 

- *primitive pure functions as programs*, `isZero`, `one`, `subtractOne` and `multiply`
- *composition*, `>-->`
- *construction*, or, equivalent, *local value definition and usage*, `Let { ... } In { ... }`
- *condition* , or, equivalent, *if-then-else logic*  `If( ... ) { ... } Else { ... }`

This may not come as a surprise to you.

This corresponds exactly with the function-level programming system `FP` of *John Backus*.

What may be surprising is that `factorial` makes use of them at the *specification* level.

`factorial` is a *program specification*, or *program description* if you wish.

Compare this with the picture *Ceci n'est pas une pipe* of *René Magritte*.

The picture is not a pipe, it is a *description* of a pipe, or *specification* of a pipe if you wish.

A pipe is an *implementation* of the picture.

Ok, let's start with the core content of the course.

# The design of the `PLP` library

## `Classification`

`Classification` is, traditionally, based on `Identity` and `Composition`.

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

On its own, `identity` is not useful, but it turns out to be useful as part of *composite programs*.

`identity` is a *generic* program.

It is convenient to define a handful of *typeful names* like  `` `z>-->z` ``.

Typeful names turn out to be suggestive when used as part of composite generic programs.

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

It may not be necessary to evaluate it.

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

Typically `asProgram` is used for *primitive pure functions* for which the flexibility of separating of specifications from implementations is considered not to be important. 

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

It may not be necessary to evaluate it.

`construct` is, somewhat artificially, biased towards its first parameter.

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

It may not be necessary to evaluate them.

## Utilities

Many definitions use *auxiliary pure functions* and corresponding programs.

For example *projections out of products* and *injections into sums*.

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

`` `(z&&b)=>(z||z)` ``  decides to let `z` be an used as an argument of `Left` or `Right` based upon a `Boolean` being `true` or `false`.

## `Program`

Again, think of *programs* as specifications of generalizations of *pure functions* that, potentially, can specify effects as well.

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

`Program` simply combines `Classification` with `Function`, `Construction` and `Condition`.

`&&&` is a defined infix `extension` that is a more complex version of the declared `extension` `&&`.

`|||` is a defined infix `extension` that is a more complex version of the declared `extension` `||`.

`Let { ... } In { ... }` defines how programs can use *local values*.

`If() { ... } Else { ... }` defines how programs can use *if-then-else logic*.

Note that both `Let` and `In` are defined in a way to be able to use them in a *fluent* way.

`Scala`, being a *sca*lable *la*nguage, shines here!

## Exercise: power of expression

It turns that `&&&` and `Let { ... } In { ... }` have the same power of expression as product based value construction.

It turns that `|||` and `If( ... ) { ... } Else { ... }` have the same power of expression as sum based conditional logic.

Below are the programs that, in a way, can be seen as *constructive categorization proofs* of statements above.

Note that they are proofs in the world of programs (specifications, or categories if you like, of generalizations of functions).

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

We needed a few extra auxiliary pure functions and corresponding programs.

This is a recurring theme. 

Eventually, we will have enough of them.

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

def `(z||z)>-->z`[
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

You may argue that program-level programming using auxiliary programs like `` `(z&&y&&x)>-->(y&&x)` `` to explicitly project values out of products is more trouble than it's worth.

Program-level programming promotes writing many small programs and combining them using program capabilities to larger composite ones.

As such the size of products stays under control.

Good programmers write *baby code* anyway.

## Main programs

*Main programs* are programs of type `Unit >--> Unit`.

Programs are combined *internally* in a *type-safe* way using program capabilities like `Composition`, `Construction` and `Condition`.

Main programs are combined *externally* using I/0 capabilities like terminals, files and sockets.

Let's add two extra program capabilities, `Reading` for *input* and `Writing` for *output*. 

## `Reading` and `Writing`

```scala
package plp.external.specification.reading

trait Reading[+R, >-->[- _, + _]]:

  // declared

  def read: Unit >--> R

  // defined

  def `u>-->r`: Unit >--> R =
    read
```

`Reading` specifies, using `read` and corresponding `` `u>-->r` ``,  that programs can *read values*.

Reading is an effect that, typically, is implemented by interacting with the external world.

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

trait Writing[-W: Writable, >-->[- _, + _]]:

  // declared
  
  def write: W >--> Unit

  // defined

  def `w>-->u`: W >--> Unit =
    write
```

`Writing` specifies, using `write` and corresponding `` `w>-->u` ``, that programs can *write things that can be written*

Writing is an effect that, typically, is implemented by interacting with the external world.

## `toMain`

Using `toMain` below, a program can be combined with reading and writing to obtain a *main program*.

Note that reading resp. writing happens when beginning resp. when ending, but both can happen at any time.

```scala
package plp.external.specification.program.main

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.reading.Reading

import plp.external.specification.writing.{
  Writable
  , ConvertibleToWritable
  ,  Writing
}

def toMain[
  Z, Y
  , W: Writable
  , >-->[- _, + _]
    : [>-->[- _, + _]] =>> Reading[Z, >-->]
    : Program
    : [>-->[- _, + _]] =>> ConvertibleToWritable[(Z && Y), W, >-->]
    : [>-->[- _, + _]] =>> Writing[W, >-->]
](`z>-->y`: Z >--> Y) =

  val reading = summon[Reading[Z, >-->]]
  import reading.{
    read => `u>-->z`
  }

  val program = summon[Program[>-->]]
  import program.Let    

  val convertibleToWritable = summon[ConvertibleToWritable[(Z && Y), W, >-->]]
  import convertibleToWritable.{
    convert => `z&&y>-->w`
  }

  val writing = summon[Writing[W, >-->]]
  import writing.`w>-->u`

  val `z&&y>-->u`: (Z && Y) >--> Unit = 
    `z&&y>-->w` >--> `w>-->u`

  `u>-->z`
    >--> {
      Let { 
        `z>-->y`
      } In { 
        `z&&y>-->u`
      }
    }
```

From a produced argument of type `Z`, `Let { ... }` constructs, using `` `z>-->y` `` a local result value of type `Y` that, together with the argument, is available for consumption in `In { ... }` using `` `(z&&y)>-->u` ``.

When using `>-->` instead of `Let { ... } In { ... }` the produced argument would not be available for consumption.

## `Materialization`

```scala
package plp.external.specification.materialization

trait Materialization[>-->[- _, + _], -Z, +Y]:

  // declared

  val materialize: (Unit >--> Unit) => Z ?=> Y
```

Main programs are *materialized* to functions of type `Z ?=> Y`.

From a type system point of view, a function of type `Z ?=> Y` is, when `given` an argument of type `Z`, essentially the same as its result of type `Y`.

As stated before, the types `Z` and `Y` reflect the impure capabilities used by the programs of that are part of the main program.

## `Computation`

Again, think of computations as specifications of generalizations of *expressions* that can also specify effects, and of *executing computations* as a generalizing of *evaluating expressions*.

Computations specifications are only used internally to implement programs with.

```scala
package plp.internal.specification.computation

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.binding.Binding

private[psbp] trait Computation[C[+ _]] 
  extends Resulting[C] 
  with Binding[C]
```

`Computation` simply combines `Resulting` and `Binding`.

## `ContextNaturalTransformation`

Computations are specified using a unary type constructor `C[+ _]` that is covariant in its type argument.

*Context natural transformations* are like context functions, but they act at, *generically*, the unary type constructor level instead of at the type level.

```scala
package plp.internal.specification.contextNaturalTransformation

private[plp] trait ?~>[-F[+ _], +G[+ _]]:

  // declared

  private[plp] def apply[Z]: F[Z] ?=> G[Z]

extension [F[+ _], G[+ _], H[+ _]] (`f?~>g`: F ?~> G) 
  private[plp] def ?~>(`g?~>h`: => G ?~> H): F ?~> H =
    new {
      override private[plp] def apply[Z]: F[Z] ?=> H[Z] =
        given G[Z] = `f?~>g`.apply
        `g?~>h`.apply
    }
```

Using `apply`, natural transformations can be *applied* in the same way as functions.

Using `?~>`, natural transformations can be *composed* in a similar way as functions.

Note that the infix `extension` `?~>` is not part of the `~>` `trait`.

We are not specifying a higher kinded type class here.

## `Resulting`

```scala
package plp.internal.specification.resulting

import plp.internal.specification.contextNaturalTransformation.?~>

private[plp] trait Resulting[C[+ _]]:

  private[plp] type I[+Z] = Z

  private[plp] def `i?~>c`: I ?~> C

  private[plp] def result[Z]: I[Z] ?=> C[Z] =
    `i?~>c`.apply
```

`Resulting` specifies, using `` `i?~>c` ``, that an expression can, naturally, be transformed to a *pure computation* that, when executed, evaluates the expression.

This is the way to, naturally, transform from the world of expressions to the world of computations.

Think of that trivial pure computation as an expression.

## `Joining`

```scala
package plp.internal.specification.joining

import plp.internal.specification.contextNaturalTransformation.?~>

private[plp] trait Joining[C[+ _]]:
        
  private[plp] type CC[+Z] = C[C[Z]]

  private[plp] def `cc?~>c`: CC ?~> C

  private[plp] def join[Z]: CC[Z] ?=> C[Z] =
    `cc?~>c`.apply
```

`Joining` specifies, using `` `cc?~>c` ``, that a *nested computation* can, naturally, be transformed to a computation.

The result of executing an *inner computation* is, trivially, used by an *outer computation* to continue execution with. 

Think of nested computation execution as nested expression evaluation.

## `Binding`

```scala
package plp.internal.specification.binding

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.joining.Joining

import plp.internal.specification.contextNaturalTransformation.?~>

private[plp] trait Binding[C[+ _]]
  extends Joining[C]:

  // declared

  private[plp] def bind[Z, Y] (
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y]

  // defined

  extension [Z, Y] (cz: C[Z]) 
    private[plp] def >=(`z=>cy`: => Z => C[Y]): C[Y] =
      bind(cz, `z=>cy`)
        
  override private[plp] def `cc?~>c`: CC ?~> C =
    new {
      override private[plp] def apply[Z]: CC[Z] ?=> C[Z] =
        summon[CC[Z]] >= 
          identity
    }
```

`Binding` specifies, using `bind`, that (the result of executing) an inner computation can be *bound* to a *continuation* that turns it into an outer computation to continue execution with. 

`` `cc?~>c` `` can be defined in terms of `>=` by simply using the `identity` continuation.

Thinking of computation execution as expression evaluation this makes sense.

The continuation simply continues evaluating.

This is a good example of an interesting usage of `identity`, this time `identity` at the function-level.

Interesting examples of using `identity` at program-level will follow.

## Exercise: define `bind` using `join`

What about power of expression?

Do `join` and `bind` have the same power of expression?

The answer is: in theory, no, in practice, almost always yes.

**Hint** This exercise need an extra computation capability that, in practice, is almost always avaialable. 

```scala
package plp.exercises

import plp.internal.specification.joining.Joining

private[plp] trait FunctionLifting[C[+ _]]:

  private[plp] def lift[Z, Y]: (Z => Y) => (C[Z] => C[Y])

private[plp] def bindUsingJoin[
  Z, Y
  , C[+ _]: Joining
          : FunctionLifting
] (
  cz: C[Z]
  , `z=>cy`: => Z => C[Y]
): C[Y] =
  
  val joining: Joining[C] = summon[Joining[C]]
  import joining.join // `cc?~>c`

  val lifting: FunctionLifting[C] = summon[FunctionLifting[C]]
  import lifting.lift

  given C[C[Y]] = lift(`z=>cy`)(cz)

  join
```

The extra computation capability  is `FunctionLifting`, the capability to *lift functions* from the value level to the computation level. 

Note that `Resulting` can be seen as the capability to *lift values* from the value level to the computation level. 

We are now ready for a very important `given`, or *constructive categorization proof* if you like.

## `ProgramFromComputation`

```scala
package plp.external.implementation.computation

private[psbp] type ProgramFromComputation[C[+ _]] = [Z, Y] =>> Z ?=> C[Y]
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

import plp.external.implementation.toFunction

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
    result
  
  override def andThen[Z, Y, X](
    `z>-->y`: Z `=>C` Y
    , `y>-->x`: => Y `=>C` X): Z `=>C` X =
      `z>-->y` >= toFunction(`y>-->x`)  

  override def toProgram[Z, Y]: (Z => Y) => (Z `=>C` Y) = 
    `z=>y` => 
      given Y = `z=>y`(summon[Z])
      identity

  override def construct[Z, Y, X](
      `z>-->y`: Z `=>C` Y
      , `z>-->x`: => Z `=>C` X): Z `=>C` (Y && X) =
    `z>-->y` >= {
      y =>
      `z>-->x` >= {
        x =>
          given (Y, X) = (y, x)
          identity
      }
    }

  override def conditionally[Z, Y, X](
      `y>-->z`: => Y `=>C` Z, 
      `x>-->z`: => X `=>C` Z): (Y || X) `=>C` Z =
    foldSum(toFunction(`y>-->z`), toFunction(`x>-->z`))(summon[Y || X]) 
```

where

```scala
package plp.external.implementation

private[plp] def toFunction[Z, Y](`z?=>y`: Z ?=> Y): Z => Y =
  z =>
    given Z = z
    `z?=>y`
```

Just like *functions can be defined in terms of expressions*, *programming capabilities* can be defined in terms of *computing capabilities*.

Note that the `given` has name `givenProgramFromComputation`.

`givenProgramFromComputation` is a generic `given` that is used to define *specific* `given`'s where it is `import`ed by *name*.

Specific `given`'s, and generic `givens` that are only `import`ed by type do need to have a name.
`
## `Transformation`

How do we go from the *pure function-level programming world* to the, potentially, *impure program-level programing world*.

Specifying capabilities is relatively simple.

Just declare a capability with an appropriate type.

In fact, that is what already has been done for `Reading` resp. `Writing` using `read` resp. `write`.

Implementing those capabilities is where *transformations* are used.

Transformations of a computation type `[Y] =>> C[Y]` *enrich* the corresponding program type `[Z, Y] =>> ProgramFromComputation[C][Z, Y]` with implementations of extra, eventually potenially impure program capabilities. 

```scala
package plp.internal.specification.transformation

import plp.internal.specification.resulting.Resulting

import plp.internal.specification.contextNaturalTransformation.?~>
  
private[plp] trait Transformation[
  D[+ _]: Resulting 
  , C[+ _]
] extends Resulting[C]:

  // declared

  private[plp] val `d?~>c`: D ?~> C

  // defined

  override private[plp] def `i?~>c`: I ?~> C = 

    val resulting = 
      summon[Resulting[D]]
    import resulting.{
      `i?~>c` => `i?~>d`
    }

    `i?~>d` ?~> `d?~>c`
```

A *transformation* uses a context natural transformation `d?~>c` of type `D ?~> C` to naturally define `i?~>c` for `C` as a composition of `i?~>d` for `D` and `d?~>c`.

Note the, hopefully, suggestive usage of typeful names, this time for context natural transformations.

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
  , D[+ _]: Computation
]: Reading[
  R
  , ProgramFromComputation[ReadingTransformed[R, D]]
] with

  private type C[+Y] = ReadingTransformed[R, D][Y]

  private type `?=>C` = [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    result => resultD
  }

  override def read: Unit `?=>C` R =
    given Unit = ()
    resultD
```

Defining `read` for `` `=>C` `` uses `()` as a `given` of type `Unit` for `resultD`.

Note that the `given` has name `givenReadingTransformedReading`.

`givenReadingTransformedReading` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

## `readingTransformedComputation`

```scala
package plp.internal.implementation.computation.transformation.reading

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.contextNaturalTransformation.?~>

import plp.internal.specification.transformation.Transformation

private[plp] given givenReadingTransformedComputation[
  R
  , D[+ _]: Computation
]: Transformation[
  D
  , ReadingTransformed[R, D]
] with Computation[ReadingTransformed[R, D]] with

  private type C[+Y] = ReadingTransformed[R, D][Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    bind => bindD
  }

  override private[plp] val `d?~>c`: D ?~> C = 
    new {
      def apply[Z]: D[Z] ?=> C[Z] =
        summon[D[Z]]
    }  

  override private[plp] def bind[Z, Y](
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y] =
    bindD(cz, z => `z=>cy`(z))
```

When defining `d?~>c` and `bind`, the type system can take the to be `given` argument of type `R` into account to check type correctness.

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
  , D[+ _]: Computation
          : [D[+ _]] =>> Materialization[
            ProgramFromComputation[D]
            , Z
            , Y
            ]
  , Z, Y
]: Materialization[
  ProgramFromComputation[ReadingTransformed[R, D]]
  , Z, 
  R ?=> D[Y]
] with

  private type C[+Z] = ReadingTransformed[R, D][Z]

  private type `?=>D`= [Z, Y] =>> ProgramFromComputation[D][Z, Y]
  private type `?=>C`= [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val materialization = summon[Materialization[`?=>D`, Z, Y]]
  import materialization.{ 
    materialize => materializeF 
  }

  private val computation = summon[Computation[D]]
  import computation.{ 
    result => resultD
    , bind => bindD 
  }

  override val materialize: (Unit `?=>C` Unit) => Z ?=> (R ?=> D[Y]) =
    `u?=>cu` =>
      given u: Unit = ()
      bindD(
        `u?=>cu`
        , _ =>
          given Y = materializeF(resultD)
          resultD
      )
```

Defining `materialize` for `` `?=>C` `` in terms of `materialize` of `` `?=>D` `` is a bit more complex.

Defining a local `given Y` for `resultD` does the trick.

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
  , D[+ _]: Computation
]: Writing[
  W
  , ProgramFromComputation[WritingTransformed[W, D]]
] with

  private type C[+Y] = WritingTransformed[W, D][Y]

  private type `?=>C` = [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    result => resultD
  }

  override def write: W `?=>C` Unit =
    given (W, Unit) = (summon[W], ())
    resultD
```

Defining `write` for `` `?=>C` `` tuples `summon[W]` to define a `given (W, Unit)` for `resultD`.

Note that the `given` has name `givenWritingTransformedWriting`.

`givenWritingTransformedWriting` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

## `givenWritingTransformedComputation`

```scala
package plp.internal.implementation.computation.transformation.writing

import plp.external.specification.types.&&

import plp.external.specification.writing.Writable

import plp.external.implementation.computation.ProgramFromComputation

import plp.internal.specification.computation.Computation

import plp.internal.specification.contextNaturalTransformation.?~>

import plp.internal.specification.transformation.Transformation

private[plp] given givenWritingTransformedComputation[
  W : Writable
  , D[+ _]: Computation
]: Transformation[
  D
  , WritingTransformed[W, D]
] with Computation[WritingTransformed[W, D]] with 

  private type C[+Y] = WritingTransformed[W, D][Y]

  private type `?=>C` = [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val computation = summon[Computation[D]]
  import computation.{ 
    result => resultD
    , bind => bindD
  }

  private val writable = summon[Writable[W]]
  import writable.{
    nothing
    , append
  }  

  override private[plp] val `d?~>c`: D ?~> C = new {
    def apply[Z]: D[Z] ?=> C[Z] =
      bindD(
        summon[D[Z]]
        , z => 
            given (W && Z) = (nothing, z)
            resultD
      )
  }  

  override private[plp] def bind[Z, Y](
    cz: C[Z]
    , `z=>cy`: => Z => C[Y]
  ): C[Y] =
    bindD(cz, (w1, z) =>
      val (w2, y): W && Y = `z=>cy`(z)
      given (W && Y) = (append(w1, w2), y)
      resultD
    )
```

Defining `d~>c` uses `nothing`.

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
  , D[+ _]: Computation
          : [D[+ _]] =>> Materialization[
            ProgramFromComputation[D]
            , Z
            , Y
            ]
  , Z, Y
]: Materialization[
  ProgramFromComputation[WritingTransformed[W, D]]
  , Z
  , D[(W, Y)]
] with

  private type C[+Z] = WritingTransformed[W, D][Z]

  private type `?=>D`= [Z, Y] =>> ProgramFromComputation[D][Z, Y]
  private type `?=>C`= [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val Materialization = summon[Materialization[`?=>D`, Z, Y]]
  import Materialization.{ 
    materialize => materializeF 
  }

  private val computation = summon[Computation[D]]
  import computation.{ 
    result => resultD
    , bind => bindD 
  }  

  override val materialize: (Unit `?=>C` Unit) => Z ?=> D[(W, Y)] =
    `u=>cu` =>
      given Unit = ()
      bindD(
        `u=>cu`
        , (w, _) =>
          val y = materializeF(resultD)
          given (W, Y) = (w, y)
          resultD
      )
```

Again, defining `materialize` for `` `?=>C` `` in terms of `materialize` of `` `?=>D` `` is a bit more complex.

Again, defining a local `given (W, Y)` for `resultD` does the trick.

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
     : [W] =>> Writing[W, ProgramFromComputation[D]]
  , D[+ _]
]: Writing[
  W
  , ProgramFromComputation[ReadingTransformed[R, D]]
] with

  private type C[+Y] = ReadingTransformed[R, D][Y]

  private type `?=>D` = [Z, Y] =>> ProgramFromComputation[D][Z, Y]
  private type `?=>C` = [Z, Y] =>> ProgramFromComputation[C][Z, Y]

  private val writing: Writing[W, `?=>D`] = summon[Writing[W, `?=>D`]]
  import writing.{
    write => writeF
  }

  override def write: W `?=>C` Unit =
    writeF
```

When defining `write` for `` `?=>C` ``, the type system can take the to be `given` argument of type `R` into account to check type correctness.

Note that the `given` has name `givenReadingTransformedWriting`.

`givenReadingTransformedWriting` is a generic `given` that is used to define specific `given`'s where it is is `import`ed by name.

All machinery is now available to define `given` implementations of the `Computation`, `Program`, `Reading` and `Writing`, and corresponding `Materialization` specification `trait`'s.

Let's start with `Computation`, and corresponding `Materialization`.

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

import plp.internal.specification.contextNaturalTransformation.?~>

import plp.internal.specification.computation.Computation

private[plp] given Computation[Active] with

  private[plp] def `i?~>c`: I ?~> Active =
    new {
      override private[plp] def apply[Z]: I[Z] ?=> Active[Z] =
        summon[Z]
    }
    
  private[plp] def bind[Z, Y](
    cz: Active[Z]
    , `z=>cy`: => Z => Active[Y]
  ): Active[Y] =
    `z=>cy`(cz)
```

Defining `` `i~>c` `` and `bind` is trivial.

## `activeMaterialization`

```scala
package plp.external.implementation.active

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.`=>A`

given Materialization[`=>A`, Unit, Unit] with
  override val materialize = 
    identity[Unit ?=> Unit]
```

Defining `materialize` is trivial as well.

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

The code above, and also some of the code below, consists of defining a new `given` in terms of existing `given`'s that are made available using dependency injection by `import`.

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

Note that `ActiveWritingReading[W, R] = [Y] =>> ReadingTransformed[R, WritingTransformed[W, Active]][Y]`, a composition of two transformations.

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

import plp.internal.specification.computation.Computation

import plp.external.implementation.active.writing.{
  given Computation[ActiveWriting[?]]
}  

import plp.external.implementation.active.writing.{
  given Materialization[`=>AW`[?], Unit, (?, Unit)]
}

given [
  W: Writable
  , R
]: Materialization[
  `=>AWR`[W, R]
  , Unit
  , R ?=> (W, Unit)
] with 

  override val materialize = 
    identity
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

Reading from standard input is type specific.

For example, for `BigInt`

```scala
package plp.external.implementation.stdIn

val stdInBigInt: StdIn[BigInt] =
  StdIn(effect = _ => BigInt(scala.io.StdIn.readInt))
```

where

```scala
package plp.external.implementation.stdIn

case class StdIn[Z](effect: Unit => Z)
```

Writing to standard output is type agnostic

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

import plp.external.specification.writing.ToString

import plp.external.specification.writing.{
  ConvertibleToWritable
}

import plp.external.implementation.stdOut.StdOut

given givenConvertibleToStdOut[
  Z: ToString
  , >-->[- _, + _]: Program
] : ConvertibleToWritable[Z, StdOut, >-->] with

  private val toString: ToString[Z] = summon[ToString[Z]]
  import toString._toString

  override private[plp] def convert: Z >--> StdOut =

    object function {

      val convert: Z => StdOut =
        z =>
          StdOut{ _ =>
            println(_toString(z))
          }

    }

    function.convert asProgram
```

where

```scala
package plp.external.specification.writing

trait ToString[Z]:
  val _toString: Z => String
```

`_toString` converts the value of type `Z` to a String.

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
```

`given Writing[StdOut, ?]`, to finish with, by writing to standard output, is defined below

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

## `mainFactorial`

Finally we can define `mainFactorial`.

```scala
package examples.implementation.active.program.stdOut.writing.stdIn.reading.factorial

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.reading.Reading

import plp.external.specification.writing.Writable

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.stdOut.StdOut

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

import plp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
  , given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, Unit)]
}

import examples.implementation.pleaseTypeAnInteger.{
  given Unit
}

import examples.implementation.stdIn.{
  given BigInt
}

import examples.implementation.stdOut.writing.factorial.{
  given ConvertibleToWritable[(BigInt && BigInt), StdOut, ?]
}

import plp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}

@main def mainFactorial(args: String*): Unit =

  materializedMainFactorial
```

The `given Unit`, to start with, `given BigInt`, to read from standard input with, and `given ConvertibleToWritable`, to convert to standard output, that is used by the code above are

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
package examples.implementation.stdOut.writing.factorial

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.implementation.stdOut.StdOut

import plp.external.implementation.stdOut.givenConvertibleToStdOut

given [
  >-->[- _, + _]: Program 
]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
  givenConvertibleToStdOut
```

where

```scala
package examples.implementation.stdOut.writing.factorial

import plp.external.specification.types.&&

import plp.external.specification.writing.ToString

given ToString[BigInt && BigInt] with 
  override val _toString: (BigInt && BigInt) => String =
    (i, j) => 
      s"applying factorial to argument $i yields result $j" 
```

It is also possible to define `verboseMainFactorial`, a more verbose version of `mainFactorial`

```scala
package examples.implementation.active.program.stdOut.writing.stdIn.reading.factorial

import plp.external.specification.types.&&

import plp.external.specification.program.Program

import plp.external.specification.writing.ToString

import plp.external.specification.reading.Reading

import plp.external.specification.writing.Writable

import plp.external.specification.writing.ConvertibleToWritable

import plp.external.specification.materialization.Materialization

import plp.external.implementation.active.writing.reading.`=>AWR`

import plp.external.implementation.stdOut.StdOut

import examples.implementation.stdOut.writing.bigInt.reading.materializedMainFactorial

import plp.external.implementation.active.writing.reading.{
  given Program[`=>AWR`[StdOut, BigInt]]
  , given Reading[BigInt, `=>AWR`[StdOut, BigInt]]
  , given Materialization[`=>AWR`[StdOut, BigInt], Unit, BigInt ?=> (StdOut, Unit)]
}

import plp.external.implementation.stdOut.{ 
  given Writable[StdOut] 
}
  
@main def verboseMainFactorial(args: String*): Unit =

  given Unit = println("Please type an integer")

  import plp.external.implementation.stdIn.stdInBigInt

  given BigInt = stdInBigInt.effect(())

  import plp.external.implementation.stdOut.givenConvertibleToStdOut

  given ToString[BigInt && BigInt] with 
    override val _toString: (BigInt && BigInt) => String =
      (i, j) => 
        s"applying factorial to argument $i yields result $j" 

  given [
    >-->[- _, + _]: Program 
  ]: ConvertibleToWritable[(BigInt && BigInt), StdOut, >-->] =  
    givenConvertibleToStdOut

  val (stdOut, _) = materializedMainFactorial

  stdOut.effect(())
```

Note that `verboseMainFactorial` is verbose about performing specified effects as `effect(())`.
