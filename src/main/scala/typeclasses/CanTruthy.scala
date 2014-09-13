package typeclasses

// See
// http://eed3si9n.com/learning-scalaz/a+Yes-No+typeclass.html
// http://learnyouahaskell.com/making-our-own-types-and-typeclasses#a-yes-no-typeclass

// Type class
trait CanTruthy[A] { self =>
  def truthys(a: A): Boolean
}

object CanTruthy {
  def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
    def truthys(a: A): Boolean = f(a)
  }
}

trait CanTruthyOps[A] {
  def self: A                  // to be defined in instance
  implicit def F: CanTruthy[A] // to be defined in instance
  def truthy: Boolean = F.truthys(self)
}

object ToCanIsTruthyOps {
  implicit def toCanIsTruthyOps[A](v: A)(implicit ev: CanTruthy[A]) =
    new CanTruthyOps[A] {
      def self = v
      implicit def F: CanTruthy[A] = ev
    }
}

object CanTruthyInstances {
  implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
    case 0 => false
    case _ => true
  })
  implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
    case Nil => false
    case _   => true
  })
  implicit val nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] = CanTruthy.truthys(_ => false)
  implicit val booleanCanTruthy: CanTruthy[Boolean] = CanTruthy.truthys(identity)
}