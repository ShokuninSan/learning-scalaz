import scalaz._
import Scalaz._

/**

  Writer Monad

  Whereas the Maybe monad is for values with an added context of failure, and the list monad is for nondeterministic values, Writer monad is for values that have another value attached that acts as a sort of log value.

  */
def isBigGang(x: Int): (Boolean, String) = (x > 9, "Compared gang size to 9.")

//implicit class PairOps[A](pair: (A, String)) {
//  def applyLog[B](f: A => (B, String)): (B, String) = {
//    val (x, log) = pair
//    val (y, newlog) = f(x)
//    (y, log ++ newlog)
//  }
//}

// Here’s how we can generalize the log to a Monoid:
implicit class PairOps[A, B: Monoid](pair: (A, B)) {
  def applyLog[C](f: A => (C, B)): (C, B) = {
    val (x, log) = pair
    val (y, newlog) = f(x)
    (y, log |+| newlog)
  }
}
(3, "Smallish gang.") applyLog isBigGang