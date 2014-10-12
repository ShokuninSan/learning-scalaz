import scalaz._
import Scalaz._

/**
 * LSZ: http://eed3si9n.com/learning-scalaz/State.html
 * LYAHFGG: http://learnyouahaskell.com/for-a-few-monads-more#state
 *
 * Very recommended article: http://softwarecorner.wordpress.com/2013/08/29/scalaz-state-monad/
 *
 */

type Stack = List[Int]

// Tedious stack/state implementation -----------------------------------------

def pop1(stack: Stack): (Stack, Int) = stack match {
  case x :: xs => (xs, x)
}

def push1(x: Int, stack: Stack): (Stack, Unit) = (x :: stack, ())

def stackManip1(stack: Stack): (Stack, Int) = {
  val (stack1, _) = push1(3, stack)
  val (stack2, _) = pop1(stack1)
  pop1(stack2)
}

stackManip1(List(5, 8, 2, 1)) === (List(8,2,1), 5)

// Using State Monad ----------------------------------------------------------

/**
 *
 * A Scala case statement can be either a Function1 or a PartialFunction
 * depending on the context.
 * - [[http://jim-mcbeath.blogspot.co.at/2009/10/scala-case-statements-as-partial.html]]
 *
 * Here, State gets a Function1, which accepts a Stack as input.
 *
 * Behind the scenes, the apply function of the related companion object does the trick
 * (simplified version):
 *
 * {{{
 *   object IndexedStateT {
 *     def apply[...](f: S1 => F[(S2, A)]): IndexedStateT[...] = new IndexedStateT[...] {
 *       def apply(s: S1) = f(s)
 *     }
 *   }
 * }}}
 *
 * So calling State.apply(stack) is actually calling IndexedStateT.apply(stack)
 *
 * @param Stack
 * @return an instance of State[Stack, Int]
 */
def pop2 = State[Stack, Int] {
  case x :: xs => (xs, x)
}

// Also push gets implemented with case statement in Function1 context
def push2(a: Int) = State[Stack, Unit] {
  case xs => (a :: xs, ())
}

/** How does this work? How and where's the actual Stack value passed?
  *
  * When looking at the signature of IndexedStateT.flatMap ...
  *
  * {{{
  *   def flatMap[S3, B](f: A => IndexedStateT[F, S2, S3, B])(implicit F: Bind[F]): IndexedStateT[F, S1, S3, B]
  * }}}
  *
  * ... we can see that only the A values (i.e. either () or Int) are passed on
  * and are visible on the left side within the for expression respectively.
  *
  * As it turns out, all calls (push2, pop2, ...) with the for expression return
  * functions, which receive values of type Stack. So, the desugared version
  * (which is calling flatMap) actually constructs a brand new function that
  * chains together the smaller functions.
  *
  * Still confused? Read section "What the hell?" in:
  *   [[http://softwarecorner.wordpress.com/2013/08/29/scalaz-state-monad/]]
  *
  */
def stackManip2: State[Stack, Int] = for {
  _ <- push2(3) // (List(5,8,2,1)) => (List(3,5,8,2,1), ()) ... () is ignored
  _ <- pop2     // (List(3,5,8,2,1)) => (List(5,8,2,1), 3)  ... 3 is ignored
  c <- pop2     // (List(5,8,2,1)) => (List(8,2,1), 5)      ... 5 is stored in c
} yield c       // (List(8,2,1)) => (List(8,2,1), c)

stackManip2(List(5,8,2,1)) === (List(8,2,1), 5)

// Using StateFunction's get and put ------------------------------------------

def stackyStack: State[Stack, Unit] = for {
  stackNow <- get
  r <- if (stackNow === List(1,2,3)) put(List(8,3,1)) else put(List(9,2,1))
} yield r

stackyStack(List(1,2,3)) === (List(8,3,1), ())
// We can also implement pop and push in terms of get and put -----------------

def pop3: State[Stack, Int] = for {
  s <- get[Stack] // s => (s, s)
  (x :: xs) = s   // destructure Stack/List
  _ <- put(xs)    // s => (s, ())
} yield x         // s => (s, x)

def push3(a: Int): State[Stack, Unit] = for {
  xs <- get[Stack]
  r <- put(a :: xs)
} yield r

def stackManip3: State[Stack, Int] = for {
  _ <- push3(3)
  _ <- pop3
  c <- pop3
} yield c

stackManip3(List(5,8,2,1)) === (List(8,2,1), 5)

// Push can be improved in terms of modify ------------------------------------

def push4(a: Int): State[Stack, Unit] = modify { xs: Stack => a :: xs }

def stackManip4: State[Stack, Int] = for {
  _ <- push4(3)
  _ <- pop3
  c <- pop3
} yield c

stackManip4(List(5,8,2,1)) === (List(8,2,1), 5)