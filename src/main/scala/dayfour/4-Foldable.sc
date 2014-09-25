import scalaz._
import Scalaz._

/**
  Foldable (see also [[exercises.Foldable]])

  LSZ: http://eed3si9n.com/learning-scalaz/Foldable.html

  In this chapter I skipped the examples for fold{Left, Right}, since they are
  also available in the standard library.

  Let's focus on [[scalaz.Foldable.foldMap()]]

  The signature looks as follows

  {{{

  trait Foldable[F[_]] { self =>

    /** Map each element of the structure to a [[scalaz.Monoid]], and combine the results. */
    def foldMap[A,B](fa: F[A])(f: A => B)(implicit F: Monoid[B]): B

    ...
  }

  trait FoldableOps[F[_],A] extends Ops[F[A]] {

    implicit def F: Foldable[F]

    final def foldMap[B: Monoid](f: A => B = (a: A) => a): B = F.foldMap(self)(f)
  ...
  }

  }}}

  LSZ:
    Monoid[A] gives us zero and |+|, so that’s enough information to fold things
    over. Since we can’t assume that Foldable contains a monoid we need a
    function to change from A => B where [B: Monoid]
 */

List(1,2,3) foldMap {identity} // 6
List("1", "2", "3") foldMap {_.toInt} // 6
