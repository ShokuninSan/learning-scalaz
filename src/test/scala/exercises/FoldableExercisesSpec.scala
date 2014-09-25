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
    IndexedSeq("indexedseq","fold","able").concatenate should be === "indexedseqfoldable"
  }

  "A streamFoldable" should "mappend it's values" in {
    import exercises.MonoidInstances.intAdditionMonoid
    Stream(1,2,3) foldMap (identity) should be === 6
  }

  "A streamFoldbable" should "concatenate it's values" in {
    import exercises.MonoidInstances.stringMonoid
    Stream("stream","fold","able").concatenate should be === "streamfoldable"
  }

  "A treeFoldable" should "foldRight" in {
    import exercises.MonoidInstances.stringMonoid
    (Branch(Branch(Leaf("able"),Leaf("fold")), Leaf("tree")): Tree[String])
      .foldRight(Monoid[String].zero){_+_} should be === "treefoldable"
  }

  "A treeFoldable" should "foldLeft" in {
    import exercises.MonoidInstances.stringMonoid
    (Branch(Branch(Leaf("able"),Leaf("fold")), Leaf("tree")): Tree[String])
      .foldLeft(Monoid[String].zero){_+_} should be === "treefoldable"
  }

  "A treeFoldable" should "foldMap" in {
    import exercises.MonoidInstances.intAdditionMonoid
    (Branch(Branch(Leaf(1),Leaf(2)), Leaf(3)): Tree[Int]).foldMap (identity) should be === 6
  }

  "A treeFoldable" should "concatenate it's values" in {
    import exercises.MonoidInstances.stringMonoid
    (Branch(Branch(Leaf("able"),Leaf("fold")), Leaf("tree")): Tree[String]).concatenate should be === "treefoldable"
  }

}
