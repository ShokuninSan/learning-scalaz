package exercises

/** FPIS: Here we’re abstracting over a type constructor F, much like we did
  * with the Parser type in the previous chapter.
  *
  * We write it as F[_], where the underscore indicates that F is not a type
  * but a type constructor that takes one type argument.
  *
  * Just like functions that take other functions as arguments are called
  * higher-order functions, something like Foldable is a higher-order type
  * constructor or a higher-kinded type.
  *
  * @tparam F
  */
trait Foldable[F[_]] {
  def foldRight[A,B](as: F[A])(z: B)(f: (A,B) => B): B
  def foldLeft[A,B](as: F[A])(z: B)(f: (B,A) => B): B
  def foldMap[A,B](as: F[A])(f: A => B)(implicit mb: Monoid[B]): B
  def concatenate[A](as: F[A])(m: Monoid[A]): A = foldLeft(as)(m.zero)((b,a) => m.op(b,a))
}


object Foldable {

  /** FPIS: Exercise 10.12 Implement Foldable[List], Foldable[IndexedSeq] and
    * Foldable[Stream].
    */
  implicit def listFoldable[A]: Foldable[List] = new Foldable[List] {
    override def foldRight[A, B](as: List[A])(z: B)(f: (A, B) => B): B = as.foldRight(z)(f)
    override def foldLeft[A, B](as: List[A])(z: B)(f: (B, A) => B): B = as.foldLeft(z)(f)
    override def foldMap[A, B](as: List[A])(f: (A) => B)(implicit mb: Monoid[B]): B = as.foldLeft(mb.zero)((b,a) => mb.op(f(a),b))
  }

  implicit def indexedSeqFoldable[A]: Foldable[IndexedSeq] = new Foldable[IndexedSeq] {
    override def foldRight[A, B](as: IndexedSeq[A])(z: B)(f: (A, B) => B): B = as.foldRight(z)(f)
    override def foldLeft[A, B](as: IndexedSeq[A])(z: B)(f: (B, A) => B): B = as.foldLeft(z)(f)
    override def foldMap[A, B](as: IndexedSeq[A])(f: (A) => B)(implicit mb: Monoid[B]): B = as.foldLeft(mb.zero)((b,a) => mb.op(f(a),b))
  }

  implicit def streamFoldable[A]: Foldable[Stream] = new Foldable[Stream] {
    override def foldRight[A, B](as: Stream[A])(z: B)(f: (A, B) => B): B = as.foldRight(z)(f)
    override def foldLeft[A, B](as: Stream[A])(z: B)(f: (B, A) => B): B = as.foldLeft(z)(f)
    override def foldMap[A, B](as: Stream[A])(f: (A) => B)(implicit mb: Monoid[B]): B = as.foldLeft(mb.zero)((b,a) => mb.op(f(a),b))
  }
  
}

/** The implementations below are generic "extensions" to be used for arbitrary
  * types like List, Option, etc.
  *
  * Note that concrete typeclass instances are required just for the
  * "Foldable[F[_]]" trait above.
  *
  * FoldableOps.toFoldableOps converts such "standard" types into Foldables by
  * building on the implicit typeclass instances (like Foldable.listFoldable).
  *
  * @tparam F A higher kinded type (* -> *) -> *
  * @tparam A A type * -> *
  */
trait FoldableOps[F[_],A] {
  val self: F[A]
  implicit def F: Foldable[F]
  def foldMap[B](f: A => B)(implicit ev: Monoid[B]): B = F.foldMap(self)(f)
  def concatenate(implicit ev: Monoid[A]): A = F.concatenate(self)(ev)
}

object FoldableOps {
  implicit def toFoldableOps[F[_], A](v: F[A])(implicit ev: Foldable[F]) = new FoldableOps[F,A] {
    val self = v
    implicit def F: Foldable[F] = ev
  }
}