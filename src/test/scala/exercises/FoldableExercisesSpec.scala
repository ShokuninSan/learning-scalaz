package exercises

import org.scalatest.{Matchers, FlatSpec}
import exercises.Foldable.listFoldable
import exercises.FoldableOps._

class FoldableExercisesSpec extends FlatSpec with Matchers {

  "A listFoldable" should "mappend it's values" in {
    import exercises.MonoidInstances.intAdditionMonoid
    List(1,2,3) foldMap (identity) should be === 6
  }

  "A listFoldbable" should "concatenate it's values" in {
    import exercises.MonoidInstances.stringMonoid
    List("list","fold","able").concatenate should be === "listfoldable"
  }
}
