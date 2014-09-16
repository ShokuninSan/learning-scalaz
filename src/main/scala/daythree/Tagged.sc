import scalaz._
import Scalaz._

/**

  Tagged type

  LSZ: http://eed3si9n.com/learning-scalaz/Tagged+type.html
  LYAHFGG: http://learnyouahaskell.com/functors-applicative-functors-and-monoids#the-newtype-keyword

 */

sealed trait KiloGram
def KiloGram[A](a: A): A @@ KiloGram = Tag[A, KiloGram](a)
val mass = KiloGram(20.0)
2 * mass

sealed trait JoulePerKiloGram
def JoulePerKiloGram[A](a: A): A @@ JoulePerKiloGram = Tag[A, JoulePerKiloGram](a)
/*

 Now we can distinguish from plain Double values (thus gain more type-safety) but still
 can operate with that value as a Double (i.e. use * with other Double values, etc.) without
 needing to unbox that value somehow. Really neat!

 */
def energyR(m: Double @@ KiloGram): Double @@ JoulePerKiloGram =
  JoulePerKiloGram(299792458.0 * 299792458.0 * m)
energyR(mass)

// Passing a plain Double does not compile, which is exactly what we want:
// energyR(10.0)

/*

 As already mentioned by Eugene, Eric Torreborre (@etorreborre) wrote an interesting article on
 Practical uses for Unboxed Tagged Types showing another use case for Tagged types:

 http://etorreborre.blogspot.co.at/2011/11/practical-uses-for-unboxed-tagged-types.html

 */