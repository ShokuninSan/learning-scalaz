import scalaz._
import Scalaz._

/**

  Functor laws

  LSZ: http://eed3si9n.com/learning-scalaz/Functor+Laws.html

 */

/*

  Law #1 ----------------------------------------------------------------------

  LYAHFGG:
  All functors are expected to exhibit certain kinds of functor-like properties
  and behaviors. … The first functor law states that if we map the id function
  over a functor, the functor that we get back should be the same as the
  original functor.
 */
(List(1, 2, 3) map {identity}) === List(1, 2, 3)

/*
  Law #2 ----------------------------------------------------------------------

  The second law says that composing two functions and then mapping the
  resulting function over a functor should be the same as first mapping one
  function over the functor and then mapping the other one.
 */
(List(1,2,3) map ({(_: Int) + 1} map {(_: Int) * 2})) === (List(1,2,3) map {(_:Int) + 1} map {(_:Int) * 2})

/*

  ScalaCheck ------------------------------------------------------------------
  Scalaz 7 ships with ScalaCheck bindings to test properties defined in e.g.
   [https://github.com/scalaz/scalaz/blob/scalaz-seven/core/src/main/scala/scalaz/Functor.scala#L68-77]

> test:console
[info] Starting scala interpreter...
[info]
import scalaz._
import Scalaz._
import scalacheck.ScalazProperties._
import scalacheck.ScalazArbitrary._
import scalacheck.ScalaCheckBinding._
...
scala> functor.laws[List].check
+ functor.identity: OK, passed 100 tests.
+ functor.composite: OK, passed 100 tests.
 */
