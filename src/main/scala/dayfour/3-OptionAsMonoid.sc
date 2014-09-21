import scalaz._
import Scalaz._

/**
  Option as Monoid

  LSZ: http://eed3si9n.com/learning-scalaz/Option+as+Monoid.html

  LYAHFGG:

    One way is to treat Maybe a as a monoid only if its type parameter a is a
    monoid as well and then implement mappend in such a way that it uses the
    mappend operation of the values that are wrapped with Just.

  */
implicit def optionMonoid[A: Semigroup]: Monoid[Option[A]] = new Monoid[Option[A]] {
  // Context bound A: Semigroup says that A must support |+|.
  def append(f1: Option[A], f2: => Option[A]) = (f1, f2) match {
    case (Some(a1), Some(a2)) => Some(Semigroup[A].append(a1, a2))
    case (Some(a1), None)     => f1
    case (None, Some(a2))     => f2
    case (None, None)         => None
  }

  def zero: Option[A] = None
}

// So type String must support |+|, to get a monoidal Option type
(none: Option[String]) |+| "andy".some

/*
  LYAHFGG:

  But if we don’t know if the contents are monoids, we can’t use mappend between
  them, so what are we to do? Well, one thing we can do is to just discard the
  second value and keep the first one. For this, the First a type exists.
 */
trait NonMonoid {
  def foo: Unit
}

val nonMonoid = new NonMonoid {
  def foo = ()
}

// This does not work, since NonMonoid has no |+|:
(none: Option[NonMonoid]) |+| nonMonoid.some

// Therefore we can do use [Tags.First]
Tags.First(none: Option[NonMonoid]) |+| Tags.First(nonMonoid.some) // returns the right value