package dayfour

import scalaz._
import Scalaz._
import org.scalatest.{Matchers, FlatSpec}
import org.scalacheck.Arbitrary

class BreakingTheLawSpec extends FlatSpec with Matchers {

  "Mapping a function" should "append a string" in {
    val coption: COption[String] = CSome(0, "ho")
    (coption map {(_: String) + "ha"}) should be === CSome(1,"hoha")
  }

  "Mapping 'identity'" should "break law #1" in {
    val csome = (CSome(0, "ho"): COption[String]) map {identity}
    csome shouldNot be === CSome(0, "ho")
    csome should be === CSome(1, "ho")
  }

  "ScalaCheck bindings" should "help checking the laws" in {
    import scalacheck.ScalazProperties._
    import scalacheck.ScalazArbitrary._
    import scalacheck.ScalaCheckBinding._

    // LSZ: This is pretty cool. ScalaCheck on its own does not ship map method, but Scalaz injected it as a Functor[Arbitrary]!
    implicit def COptionArbitrary[A](implicit a: Arbitrary[A]): Arbitrary[COption[A]] = a map { a => (CSome(0, a): COption[A]) }

    functor.laws[COption].check
    /* COption is breaking the laws, so this call print results to stdout like:

      Testing started at 22:55 ...
      ! functor.identity: Falsified after 0 passed tests.
      > ARG_0: CSome(0,-1)
      ! functor.composite: Falsified after 0 passed tests.
      > ARG_0: CSome(0,8986462)
      > ARG_1: <function1>
      > ARG_2: <function1>
     */

    functor.laws[List].check
    /* List obey the laws, thus the result looks as follows:

      + functor.identity: OK, passed 100 tests.
      + functor.composite: OK, passed 100 tests.
     */
  }
}
