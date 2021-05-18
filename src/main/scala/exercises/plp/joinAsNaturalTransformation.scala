// package plp.exercises

// import plp.internal.specification.binding.Binding
      
// import plp.internal.specification.naturalTransformation.~>

// private[plp] def joinAsNaturalTransformation[C[+ _]: Binding]: ([Z] =>> C[C[Z]]) ~> C =
//   val binding: Binding[C] = summon[Binding[C]]
//   import binding.join

//   new {
//     override def apply[Z]: C[C[Z]] => C[Z] =
//       join
//   } 