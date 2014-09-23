package exercises

trait Monoid[A] { self =>
  def op(a1: A, a2: => A): A
  def zero: A
}

object Monoid {
  def apply[A](implicit ev: Monoid[A]) = ev
  def zero[A](implicit F: Monoid[A]) = F.zero
}


trait MonoidOps[A] {
  val self: A
  implicit def F: Monoid[A]
  def |+|(a: A): A = F.op(self, a)
  def zero = F.zero
}

object MonoidOps {
  implicit def toMonoidOps[T](v: T)(implicit ev: Monoid[T]) = new MonoidOps[T] {
    val self = v
    implicit def F = ev
  }
}

object MonoidInstances {

  implicit val stringMonoid = new Monoid[String] {
    def op(a1: String, a2: => String): String = a1 + a2
    def zero: String = ""
  }

  implicit val intAdditionMonoid = new Monoid[Int] {
    def op(a1: Int, a2: => Int): Int = a1 + a2
    def zero: Int = 0
  }

  implicit def listMonoid[A] = new Monoid[List[A]] {
    def op(a1: List[A], a2: => List[A]): List[A] = a1 ++ a2
    val zero = Nil
  }

  /** Exercise 10.1: Give Monoid instances for integer addition and multiplication as well as the Boolean operators. */
  implicit def intMultiMonoid: Monoid[Int] = new Monoid[Int] {
    def op(a1: Int, a2: => Int): Int = a1 * a2
    def zero: Int = 1
  }

  implicit def booleanOrMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    def op(a1: Boolean, a2: => Boolean): Boolean = a1 || a2
    def zero: Boolean = false
  }

  implicit def booleanAndMonoid: Monoid[Boolean] = new Monoid[Boolean] {
    def op(a1: Boolean, a2: => Boolean): Boolean = a1 && a2
    def zero: Boolean = true
  }

  /** Exercise 10.2: Give a Monoid instance for combining Option values */
  implicit def optionMonoid[A:Monoid]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def op(a1: Option[A], a2: => Option[A]): Option[A] = (a1, a2) match {
      case (Some(a), Some(b)) => Some(implicitly[Monoid[A]].op(a, b))
      case (Some(a), None) => a1
      case (None, Some(a)) => a2
      case _ => None
    }
    def zero: Option[A] = None
  }

  /** Exercise 10.3: A function having the same argument and return type is sometimes called an 'endofunction'. Write a
    * Monoid for endofunctions.
    */
  implicit def endoMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
    def op(a1: (A) => A, a2: => (A) => A): (A) => A = b => a2(a1(b))
    def zero: (A) => A = b => b
  }

}