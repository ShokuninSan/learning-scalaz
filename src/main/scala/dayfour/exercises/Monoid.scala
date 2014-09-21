package dayfour.exercises

trait Monoid[A] { self =>
  def op(a1: A, a2: => A): A
  def zero: A
}

object Monoid {

  def apply[A](implicit ev: Monoid[A]) = ev
  def zero[A](implicit F: Monoid[A]) = F.zero

}

object MonoidInstances {

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
  def optionMonoid[A]: Monoid[Option[A]] = ???

  /** Exercise 10.3: A function having the same argument and return type is sometimes called an 'endofunction'. Write a
    * Monoid for endofunctions.
    */
  def endoMonoid[A]: Monoid[A => A] = ???

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