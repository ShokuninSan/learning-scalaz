import scalaz._
import Scalaz._

/** A knights quest

  http://en.wikipedia.org/wiki/Monad_(functional_programming):

  Many common programming concepts can be described in terms of a monad
  structure, including side effects such as input/output, variable assignment,
  exception handling, parsing, nondeterminism (!), concurrency, and continuations.
  This allows these concepts to be defined in a purely functional manner,
  without major extensions to the language's semantics.

  LYAHFGG:
    Here’s a problem that really lends itself to being solved with
    non-determinism. Say you have a chess board and only one knight piece on it.
    We want to find out if the knight can reach a certain position in three
    moves.

  */
case class KnightPos(c: Int, r: Int) {
  def move: List[KnightPos] =
    for {
      KnightPos(c2, r2) <- List(KnightPos(c + 2, r - 1), KnightPos(c + 2, r + 1),
        KnightPos(c - 2, r - 1), KnightPos(c - 2, r + 1),
        KnightPos(c + 1, r - 2), KnightPos(c + 1, r + 2),
        KnightPos(c - 1, r - 2), KnightPos(c - 1, r + 2)) if (
      ((1 |-> 8) element c2) && ((1 |-> 8) contains r2))
    } yield KnightPos(c2, r2)
  def in3: List[KnightPos] =
    for {
      first <- move
      second <- first.move
      third <- second.move
    } yield third
  def canReachIn3(end: KnightPos): Boolean = in3 contains end
}

KnightPos(6, 2) canReachIn3 KnightPos(6, 1) // true
KnightPos(6, 2) canReachIn3 KnightPos(7, 3) // false