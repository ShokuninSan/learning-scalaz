import scalaz._
import Scalaz._

/**
 * LSZ: http://eed3si9n.com/learning-scalaz/State.html
 * LYAHFGG: http://learnyouahaskell.com/for-a-few-monads-more#state
 *
 * See also:
 *  - [[http://www.youtube.com/watch?feature=player_detailpage&v=XVmhK8WbRLY#t=585s An introduction to the State Monad]]
 */

type Stack = List[Int]

// Tedious stack/state implementation -----------------------------------------

def pop1(stack: Stack): (Stack, Int) = stack match {
  case x :: xs => (xs, x)
}

def push1(x: Int, stack: Stack): (Stack, Unit) = (x :: stack, ())

def stackManip1(stack: Stack): (Stack, Int) = ???

stackManip1(List(5, 8, 2, 1)) === (List(8,2,1), 5)

// Using State Monad ----------------------------------------------------------

/**
 *
 * A Scala case statement can be either a Function1 or a PartialFunction
 * depending on the context.
 * - [[http://jim-mcbeath.blogspot.co.at/2009/10/scala-case-statements-as-partial.html]]
 *
 * Here, pop returns a Function1, which accepts a Stack as input.
 *
 * @param Stack
 * @return an instance of State[Stack, Int]
 */
def pop2: State[Stack, Int] = {
  case x :: xs => (xs, x)
}

def push2(a: Int): State[Stack, Int] = {
  case xs: Stack => (a :: xs, ())
  case Nil => (a :: Nil, ())
}

def stackManip2: State[Stack, Unit] = ???

stackManip2(List(5,8,2,1)) === (List(8,2,1), 5)

// Using StateFunction's get and put ------------------------------------------

def stackyStack: State[Stack, Unit] = for {
  stackNow <- get
  r <- if (stackNow === List(1,2,3)) put(List(8,3,1)) else put(List(9,2,1))
} yield r

stackyStack(List(1,2,3)) === (List(8,3,1), ())

// We can also implement pop and push in terms of get and put -----------------

def pop3: State[Stack, Int] = ???

def push3: State[Stack, Int] = ???

def stackManip3: State[Stack, Int] = ???

stackManip3(List(5,8,2,1)) === (List(8,2,1), 5)