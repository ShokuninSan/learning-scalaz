package dayfour

import scalaz._
import Scalaz._
import org.scalatest.{FlatSpec, Matchers}
import scalaz.{@@, Tags, scalacheck}
import scalacheck.ScalazProperties._
import scalacheck.ScalazArbitrary._
import scalacheck.ScalaCheckBinding._

class ApplicativeLawsSpec extends FlatSpec with Matchers {

  "Applicative laws" should "hold" in {
    applicative.laws[List].check
    /* Result:
      + applicative.functor.identity: OK, passed 100 tests.
      + applicative.functor.composite: OK, passed 100 tests.
      + applicative.identity: OK, passed 100 tests.
      + applicative.composition: OK, passed 100 tests.
      + applicative.homomorphism: OK, passed 100 tests.
      + applicative.interchange: OK, passed 100 tests.
      + applicative.map consistent with ap: OK, passed 100 tests.
     */
  }
}
