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