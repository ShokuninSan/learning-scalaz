package dayfour

import scalaz._
import Scalaz._

/**

  Breaking the law
  */
sealed trait COption[+A] {}
case class CSome[A](counter: Int, a: A) extends COption[A]
case object CNone extends COption[Nothing]

object COption {
  implicit def coptionEqual[A]: Equal[COption[A]] = Equal.equalA
  implicit val coptionFunctor = new Functor[COption] {
    def map[A, B](fa: COption[A])(f: A => B): COption[B] = fa match {
      case CNone => CNone
      case CSome(c, a) => CSome(c + 1, f(a))
    }
  }
}
