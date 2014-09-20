package dayfour

import scalaz._
import Scalaz._
import org.scalatest.{FlatSpec, Matchers}
import scalaz.{@@, Tags, scalacheck}
import scalacheck.ScalazProperties._
import scalacheck.ScalazArbitrary._
import scalacheck.ScalaCheckBinding._

class MonoidLawsSpec extends FlatSpec with Matchers {

  "Monoid" should "satisfy left- and right identity laws" in {
    val zero = Monoid[Int @@ Tags.Multiplication].zero
    val one = Tags.Multiplication(1)
    val two = Tags.Multiplication(2)

    one * two should be === 2
    two * one should be === 2
    (zero |+| two) should be === 2
    (two |+| zero) should be === 2

    monoid.laws[Int @@ Tags.Multiplication].check
    /* Result:
      + monoid.semigroup.associative: OK, passed 100 tests.
      + monoid.left identity: OK, passed 100 tests.
      + monoid.right identity: OK, passed 100 tests.
     */
  }

}
