import scalaz._
import Scalaz._

/**
  Day 2:

  Applicative Functors

  LSZ: http://eed3si9n.com/learning-scalaz/Applicative.html
  LYAHFGG: http://learnyouahaskell.com/functors-applicative-functors-and-monoids#applicative-functors

  You can think of <*> as a sort of a beefed-up fmap. Whereas fmap takes a function and a functor and applies the function inside the functor value, <*> takes a functor that has a function in it and another functor and extracts that function from the first functor and then maps it over the second one.
  */
// 'map' just maps a function like _ + 3 over a functor, e.g Some(9):
Some(9) map {(_:Int) + 3} // Some(12)
// '<*>' maps a function inside a functor and applies this function to a value inside another functor
9.some <*> {(_:Int) + 3}.some // Some(12)
/** With ApplicativeBuilder, we can apply a plain (pure) function to Functors: */
(9.some |@| 3.some) {_ + _} // Some(12)
/*
In Haskell this looks as follows:

ghci> (+) <$> Just 3 <*> Just 9
Just 12
 */

// Let’s see if we can use <*> and |@| on Lists:

List(1, 2, 3) <*> List((_: Int) * 0, (_: Int) + 100, (x: Int) => x * x)

(List("ha", "heh", "hmm") |@| List("?", "!", ".")) {_ + _}
// Let’s try implementing a function that takes a list of applicatives and returns an applicative that has a list as its result value. We’ll call it sequenceA.
import scala.language.higherKinds
def sequenceA[F[_]: Applicative, A](list: List[F[A]]): F[List[A]] = list match {
  case Nil     => (Nil: List[A]).point[F]
  case x :: xs => (x |@| sequenceA(xs)) {_ :: _}
}

sequenceA(List(1.some, 2.some))
sequenceA(List(3.some, none, 1.some))

// For Function1 with Int fixed example, we have to unfortunately invoke a dark magic (see also https://github.com/earldouglas/scala-scratchpad/tree/master/type-lambdas).
type Function1Int[A] = ({type λ[A]=Function1[Int, A]})#λ[A] // ... sort of partial type constructor;
val function1List = List((_: Int) + 3, (_: Int) + 2, (_: Int) + 1): List[Function1Int[Int]]
val int2ListInt = sequenceA(function1List)
int2ListInt(3) // List(6, 5, 4)