package typeclasses

import org.scalatest.{Matchers, FlatSpec}
import ToCanIsTruthyOps._
import CanTruthyInstances._

class CanTruthySpec extends FlatSpec with Matchers {

  "1.truthy" should "return true" in {
    1.truthy should be === true
  }

  "0.truthy" should "return false" in {
    0.truthy should be === false
  }

  "List.truthy" should "return true" in {
    List("foo").truthy should be === true
  }

  "Nil.truthy" should "return false" in {
    Nil.truthy should be === false
  }

  "false.truthy" should "return false" in {
    true.truthy should be === true
  }

  "true.truthy" should "return true" in {
    true.truthy should be === true
  }

  "truthyIf" should "be able to evaluate CanTruthy instances" in {
    import CanTruthyControls._

    // For integers
    (truthyIf(1) { "yes" } { "no" }) should be === "yes"
    (truthyIf(0) { "yes" } { "no" }) should be === "no"

    // For Lists
    (truthyIf(List(1,2,3)) { "yes" } { "no" }) should be === "yes"
    (truthyIf(Nil) { "yes" } { "no" }) should be === "no"

    // For booleans
    (truthyIf(true) { "yes" } { "no" }) should be === "yes"
    (truthyIf(false) { "yes" } { "no" }) should be === "no"
  }
}
