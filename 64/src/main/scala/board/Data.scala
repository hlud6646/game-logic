package board 

import Console.{ RED, WHITE, RESET  }
import cats.Monoid

import ColorInstances.colorMonoid
import TokensInstances.tokensMonoid

final case class Data(
  color:  Color,
  tokens: Tokens,
  lines:  Seq[Line]) {
  private def consoleColor = color match {
    case Red   => RED
    case White => WHITE
  }
  override def toString = consoleColor + tokens.toString + Console.RESET 
}
// TODO: This method wants to be condensed to one line.
object Data {
  
  implicit val dataMonoid: Monoid[Data] = new Monoid[Data] {
    def empty = Data(
      Monoid[Color].empty,
      Monoid[Tokens].empty,
      Monoid[Seq[Line]].empty
    )
    def combine(x: Data, y: Data) = Data(
      Monoid[Color].combine(x.color, y.color), 
      Monoid[Tokens].combine(x.tokens, y.tokens),
      Monoid[Seq[Line]].combine(x.lines, y.lines)
    )
  }
}
