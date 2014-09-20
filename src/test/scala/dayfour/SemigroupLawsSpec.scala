package dayfour

import scalaz._
import Scalaz._
import org.scalatest.{FlatSpec, Matchers}
import scalaz.{@@, Tags, scalacheck}
import scalacheck.ScalazProperties._
import scalacheck.ScalazArbitrary._
import scalacheck.ScalaCheckBinding._

class SemigroupLawsSpec extends FlatSpec with Matchers {

  "Semigroups associativity law" should "hold" in {
    /*
      LSZ: Remember, 1 * (2 * 3) and (1 * 2) * 3 must hold, which is called associative.
     */
    semigroup.laws[Int @@ Tags.Multiplication].check // prints: + semigroup.associative: OK, passed 100 tests.
  }

}
