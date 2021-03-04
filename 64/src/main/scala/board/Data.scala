package board 

import Console.{ RED, WHITE, RESET  }
import cats.Monoid
import Monoids._

final case class Data(
  color:  Color,
  tokens: Tokens
) {
  private def consoleColor = color match {
    case Red   => RED
    case White => WHITE
  }
  override def toString = consoleColor + tokens.toString + Console.RESET 
}
object Data {
  implicit val dataMonoid: Monoid[Data] = new Monoid[Data] {
    def empty = Data(
      Monoid[Color].empty,
      Monoid[Tokens].empty
    )
    def combine(x: Data, y: Data) = Data(
      Monoid[Color].combine(x.color, y.color), 
      Monoid[Tokens].combine(x.tokens, y.tokens)
    )
  }
}
