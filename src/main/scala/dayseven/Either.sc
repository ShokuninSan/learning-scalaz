import scalaz._
import Scalaz._

/** Either

  LSZ: [[http://eed3si9n.com/learning-scalaz/Either.html]]
  LYAHFGG: [[http://learnyouahaskell.com/for-a-few-monads-more#error]]

  The Either e a type on the other hand, allows us to incorporate a context of
  possible failure to our values while also being able to attach values to the
  failure, so that they can describe what went wrong or provide some other
  useful info regarding the failure. An Either e a value can either be a Right
  value, signifying the right answer and a success, or it can be a Left value,
  signifying failure.

  The Either type in Scala standard library is not a monad on its own, which
  means it does not implement flatMap method with or without Scalaz.

  */
// Enter awesomeness! Since Either in Scala standard library is not a monad you
// can't do something like that:
for {
  e1 <- "event 1 ok".right
  e2 <- "event 2 failed!".left[String]
  e3 <- "event 3 failed!".left[String]
} yield (e1 |+| e2 |+| e3) // returns scalaz.\/[String,String] = -\/(event 2 failed!)
