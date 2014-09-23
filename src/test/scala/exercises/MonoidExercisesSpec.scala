package exercises

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
    (true |+| true) should be === true
    (true |+| false) should be === true
    (false |+| true) should be === true
    (false |+| false) should be === false
    (true |+| Monoid[Boolean].zero) should be === true
    (Monoid[Boolean].zero |+| true) should be === true
    (false |+| Monoid[Boolean].zero) should be === false
    (Monoid[Boolean].zero |+| false) should be === false
  }

  "booleanAndMonoid" should "conjunct values" in {
    import MonoidInstances.booleanAndMonoid
    (true |+| true) should be === true
    (true |+| false) should be === false
    (false |+| true) should be === false
    (false |+| false) should be === false
    (true |+| Monoid[Boolean].zero) should be === true
    (Monoid[Boolean].zero |+| true) should be === true
    (false |+| Monoid[Boolean].zero) should be === false
    (Monoid[Boolean].zero |+| false) should be === false
  }

  "optionMonoid" should "append Option's contents" in {
    import MonoidInstances.intAdditionMonoid
    import MonoidInstances.optionMonoid
    ((Some(1): Option[Int]) |+| Some(2)) should be === Some(3)
  }

  "endoMonoid" should "combine two endofunctions" in {
    import MonoidInstances.endoMonoid
    (((_:Int) * 2) |+| ((_:Int) + 1))(1) should be === 3
    (((a:Int) => a) |+| Monoid[Int => Int].zero)(42) should be === 42
    (Monoid[Int => Int].zero |+| ((a:Int) => a))(42) should be === 42
  }

}
