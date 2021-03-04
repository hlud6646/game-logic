package board

import cats.Monoid
import util.Random

/** A region may have various properties, for example its color, the tokens
 *  it holds, whether it is alive/dead, whether it has been visited yet.
 *  For some property P, expressed as the function
 *    P(region) = value
 *  there must be some sort of composition giving the value
 *    P(r join s) = P(r) |+| P(s).
 *  There should also be an appropriate zero value.
 *  In other words, a property is a unital magama.
 *  
 *  Let's use the monoid pattern to represent this, not worrying about 
 *  whether the operations obey the associativity laws.
 */
trait Property

sealed trait Color extends Property
object Color {
  // Combine by blending colors.
  val colorMonoid1 = new Monoid[Color] {
    def empty = White
    def combine(x: Color, y: Color) = x match {
      case White => y
      case Red   => Red
    }
  }
  // Keep the new color.
  val colorMonoid2 = new Monoid[Color] {
    def empty = White
    def combine(x: Color, y: Color) = y
  }
  val monoids = List( colorMonoid1,
                      colorMonoid2)
}
object White extends Color
object Red   extends Color


final case class Tokens(items: List[Symbol]) extends Property {
  def add(x: Symbol) = Tokens(x :: items)
}
object Tokens {
  // keep all the tokens
  val tokensMonoid1 = new Monoid[Tokens] {
    def empty = Tokens(Nil)
    def combine(x: Tokens, y: Tokens) = Tokens(x.items ++ y.items)
  }
  // keep up to 2 tokens, giving older tokens precedence.
  val tokensMonoid2 = new Monoid[Tokens] {
    def empty = Tokens(Nil)
    def combine(x: Tokens, y: Tokens) = Tokens( x.items.++(y.items).take(2)  )
  }
  val monoids = List( tokensMonoid1, 
                      tokensMonoid2)
}

object Monoids {
  implicit val colorMonoid:  Monoid[Color]  = Color.colorMonoid1
  implicit val tokensMonoid: Monoid[Tokens] = Tokens.tokensMonoid1
}
