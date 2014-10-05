import scalaz._
import Scalaz._

/**

  Reader Monad

  LSZ: http://eed3si9n.com/learning-scalaz/Reader.html
  LYAHFGG: http://learnyouahaskell.com/for-a-few-monads-more

  Not only is the function type (->) r a functor and an applicative functor, but it’s also a monad. Just like other monadic values that we’ve met so far, a function can also be considered a value with a context. The context for functions is that that value is not present yet and that we have to apply that function to something in order to get its result value.

  */

/** Both (*2) and (+10) get applied to the number 3 in this case. return (a+b) does as well, but it ignores it and always presents a+b as the result. For this reason, the function monad is also called the reader monad. All the functions read from a common source.
  *
  */
val addStuff: Int => Int = for {
  a <- (_: Int) * 2
  b <- (_: Int) + 10
} yield a + b
addStuff(3) // 19

