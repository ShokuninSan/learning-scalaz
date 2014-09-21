package dayfour.exercises

import org.scalatest.{FlatSpec, Matchers}

class MonoidExercisesSpec extends FlatSpec with Matchers {

  "intAddition Monoid" should "add Integers" in {
    import MonoidInstances.intAdditionMonoid
    import MonoidOps._
    (1 |+| 2) === 3
    (1 |+| Monoid[Int].zero) === 1
  }

}
