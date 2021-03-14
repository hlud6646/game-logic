package board

import cats.Monoid
import util.Random

object ColorInstances {

  // Keep the first color.
  val colorMonoid1: Monoid[Color] = new Monoid[Color] {
    def combine(x: Color, y: Color) = x
    def empty = White
  }

  // Blend the colors.
  val colorMonoid2 = new Monoid[Color] {
    def combine(x: Color, y: Color) = if (y == White) x else x match {
      case White => y
      case Red   => if (y == Red)   Red    else Brown
      case Green => if (y == Green) Green  else Brown 
      case Blue  => if (y == Blue)  Blue   else Brown
      case Brown => Brown
    }
    def empty = White
  }

  // Which monoid will we use!
  val m = Seq(
    colorMonoid1,
    colorMonoid2
  )
  implicit val colorMonoid = m(Random.nextInt(m.size))
}
