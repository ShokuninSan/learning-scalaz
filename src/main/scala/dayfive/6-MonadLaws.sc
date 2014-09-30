import scalaz._
import Scalaz._
import scalaz.concurrent.Future

/** Monad laws

  LSZ: http://eed3si9n.com/learning-scalaz/Monad+laws.html
  LYAHFGG: http://learnyouahaskell.com/a-fistful-of-monads#monad-laws

  */

// #1: Left identity

// The first monad law states that if we take a value, put it in a default
// context with return and then feed it to a function by using >>=, it’s the
// same as just taking the value and applying the function to it.
//
// I.e.: (Monad[F].point(x) flatMap {f}) assert_=== f(x)
// ... with the Option Monad:
(Monad[Option].point(3) >>= { x => (x + 100000).some }) === (3 |> { x => (x + 100000).some }) // true


// #2: Right identity

//The second law states that if we have a monadic value and we use >>= to feed
// it to return, the result is our original monadic value.
//
// I.e.: // (m forMap {Monad[F].point(_)}) assert_=== m
("move on up".some flatMap {Monad[Option].point(_)}) === "move on up".some // true

// #3: Associativity

// The final monad law says that when we have a chain of monadic function
// applications with >>=, it shouldn’t matter how they’re nested.
//
// I.e.: // (m flatMap f) flatMap g assert_=== m flatMap { x => f(x) flatMap {g} }
import dayfive.Pierre._

Monad[Option].point(Pole(0, 0)) >>= {_.landRight(2)} >>= {_.landLeft(2)} >>= {_.landRight(2)} // Some(Pole(2,4))

// should yield the same result as
Monad[Option].point(Pole(0, 0)) >>= { x =>
  x.landRight(2) >>= { y =>
    y.landLeft(2) >>= { z =>
      z.landRight(2)
    }
  }
} // Some(Pole(2,4))
