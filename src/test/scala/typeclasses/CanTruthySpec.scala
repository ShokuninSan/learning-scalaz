package typeclasses

import org.scalatest.{Matchers, FlatSpec}
import ToCanIsTruthyOps._
import CanTruthyInstances._

class CanTruthySpec extends FlatSpec with Matchers {

  "Int.truthy" should "return an approporiate boolean value" in {
    1.truthy should be === true
  }

  "List.truthy" should "return an appropriate boolean value" in {
    List("foo").truthy should be === true
  }

  "Nil.truthy" should "return an appropriate boolean value" in {
    Nil.truthy should be === false
  }

  "Boolean.truthy" should "return an appropriate boolean value" in {
    true.truthy should be === true
  }
}
