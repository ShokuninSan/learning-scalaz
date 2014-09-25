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

  "A indexedSeqFoldable" should "mappend it's values" in {
    import exercises.MonoidInstances.intAdditionMonoid
    IndexedSeq(1,2,3) foldMap (identity) should be === 6
  }

  "A indexedSeqFoldbable" should "concatenate it's values" in {
    import exercises.MonoidInstances.stringMonoid
    IndexedSeq("list","fold","able").concatenate should be === "listfoldable"
  }

  "A streamFoldable" should "mappend it's values" in {
    import exercises.MonoidInstances.intAdditionMonoid
    Stream(1,2,3) foldMap (identity) should be === 6
  }

  "A streamFoldbable" should "concatenate it's values" in {
    import exercises.MonoidInstances.stringMonoid
    Stream("list","fold","able").concatenate should be === "listfoldable"
  }
}
