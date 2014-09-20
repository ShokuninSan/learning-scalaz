import scalaz._
import Scalaz._

/**

  Monoids

  LSZ: http://eed3si9n.com/learning-scalaz/Monoid.html
  LYAHFGG: http://learnyouahaskell.com/functors-applicative-functors-and-monoids#monoids

  A monoid is when you have an associative binary function and a value which acts as an identity with respect to that function.

  */

// Monoid[List]
List(1, 2, 3) |+| List(4, 5, 6) // List(1,2,3,4,5,6)
// Monoid[Int] for adding
1 |+| 2 // 3
10 |+| Monoid[Int].zero // 10

/*

 Monoid[Int] for Multiplication, Ordering etc., this is where Scalaz 7 uses tagged type.
 There are 8 tags for Monoids and
 1 tag named Zip for Applicative.

 */
Tags.Multiplication(10) |+| Monoid[Int @@ Tags.Multiplication].zero // 10
Tags.Disjunction(true) |+| Tags.Disjunction(false) // true
Tags.Conjunction(true) |+| Tags.Conjunction(false) // false
(Ordering.LT: Ordering) |+| (Ordering.GT: Ordering) // Orderling.LT
(Ordering.EQ: Ordering) |+| (Ordering.GT: Ordering) // Orderling.GT
/*
  How is the Ordering Monoid useful?

  Because the left comparison is kept unless it’s Ordering.EQ we can use this to compose
  two levels of comparisons. Let’s try implementing lengthCompare using Scalaz:
  */
def lengthCompare(lhs: String, rhs: String): Ordering = (lhs.length ?|? rhs.length) |+| (lhs ?|? rhs)
lengthCompare("zen", "ants") // Ordering.LT
lengthCompare("zen", "ant") // Ordering.GT

Monoid[List[Int]].zero // Nil
Monoid[String].zero // ""