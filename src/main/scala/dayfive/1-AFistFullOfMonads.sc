import scalaz._
import Scalaz._

/** A fist full of Monads

  LSZ: http://eed3si9n.com/learning-scalaz/Monad.html

  LYAHFGG: http://learnyouahaskell.com/a-fistful-of-monads

    Monads are a natural extension applicative functors, and they provide a
    solution to the following problem: If we have a value with context, m a, how
    do we apply it to a function that takes a normal a and returns a value with
    a context.

    Functor:              fmap :: (Functor f) => (a -> b) -> f a -> f b
    Applicative Functor:  (<*>) :: (Applicative f) => f (a -> b) -> f a -> f b
    Monad:                (>>=) :: (Monad m) => m a -> (a -> m b) -> m b

  */
Monad[Option].point("WHAT")
9.some flatMap { x => Monad[Option].point(x * 10) }
(none: Option[Int]) flatMap { x => Monad[Option].point(x * 10) }

// LYAHFGG:
// In do notation, when we bind monadic values to names, we can utilize pattern
// matching, just like in let expressions and function parameters.
def justH: Option[Char] =
  for {
    (x :: xs) <- "hello".toList.some
  } yield x
justH // Some(h)

// LYAHFGG:
// When pattern matching fails in a do expression, the fail function is called.
// It’s part of the Monad type class and it enables failed pattern matching to
// result in a failure in the context of the current monad instead of making
// our program crash.
def wopwop: Option[Char] =
  for {
    (x :: xs) <- "".toList.some
  } yield x
wopwop // None
List(3, 4, 5) >>= {x => List(x, -x)}

List(3, 4, 5).flatMap {x => List(x, -x)}

// Is NOT the same as:
val list =  for {
  x <- List(3,4,5)
} yield List(x, -x)

// MonadPlus introduces filter operation:
(1 |-> 50) filter { x => x.shows contains '7' }