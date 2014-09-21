package dayfour.exercises

import org.scalatest.{FlatSpec, Matchers}
import MonoidOps._

class MonoidExercisesSpec extends FlatSpec with Matchers {

  "intAdditionMonoid" should "add Integers" in {
    import MonoidInstances.intAdditionMonoid
    (1 |+| 2) should be === 3
    (1 |+| Monoid[Int].zero) should be === 1
  }

  "intMultiMonoid" should "multiply Integers" in {
    import MonoidInstances.intMultiMonoid
    (1 |+| 2) should be === 2
    (2 |+| 1) should be === 2
    (1 |+| Monoid[Int].zero) should be === 1
    (Monoid[Int].zero |+| 1) should be === 1
  }

  "booleanOrMonoid" should "disjunct values" in {
    import MonoidInstances.booleanOrMonoid
    (true |+| false) should be === true
    (false |+| true) should be === true
    (true |+| Monoid[Boolean].zero) should be === true
    (Monoid[Boolean].zero |+| true) should be === true
    (false |+| Monoid[Boolean].zero) should be === false
    (Monoid[Boolean].zero |+| false) should be === false
  }

  "booleanAndMonoid" should "conjunct values" in {
    import MonoidInstances.booleanAndMonoid
    (true |+| false) should be === false
    (false |+| true) should be === false
    (true |+| Monoid[Boolean].zero) should be === true
    (Monoid[Boolean].zero |+| true) should be === true
    (false |+| Monoid[Boolean].zero) should be === false
    (Monoid[Boolean].zero |+| false) should be === false
  }

}
