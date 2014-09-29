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
