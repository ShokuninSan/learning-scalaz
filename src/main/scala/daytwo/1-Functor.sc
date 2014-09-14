import scalaz._
import Scalaz._

/**

Day 2: http://eed3si9n.com/learning-scalaz/Functor.html

This is interesting. Basically map gives us a way to compose functions,
except the order is in reverse from f compose g.
 */
val f: Int => Int = _ + 1
val g: Int => Int = _ * 2

val c = f compose g
c(1) // 3
val m = f map g
m(1) // 4
// LYAHFGG: http://learnyouahaskell.com/functors-applicative-functors-and-monoids

/**

In Haskell, the fmap seems to be working as the same order as f compose g:

ghci> fmap (*3) (+100) 1
303

ghci> (*3) `fmap` (+100) $ 1
303

 */

// Since map here’s an injected method of F[A], the data structure to be mapped over comes first,
// then the function comes next.
(((_: Int) * 3) map {_ + 100}) (1) // 103

// Are we going to miss out on this lifting goodness?
// There are several neat functions under Functor typeclass. One of them is called lift:
val list = Functor[List].lift {(_: Int) * 2}
list(List(3))

// Functor also enables some operators that overrides the values in the data structure
// like >|, as, fpair, strengthL, strengthR, and void:
List(1, 2, 3) >| "x"
List(1, 2, 3) as "x"
List(1, 2, 3).fpair
List(1, 2, 3).strengthL("x")
List(1, 2, 3).strengthR("x")
List(1, 2, 3).void