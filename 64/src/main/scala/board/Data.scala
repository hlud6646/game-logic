package board 

import Console.{ RED, GREEN, BLUE, YELLOW, WHITE, RESET  }
import cats.Monoid

import ColorInstances.colorMonoid
import LivingInstances.livingMonoid

final case class Data(
  color:  Color,
  tokens: Seq[Token],
  lines:  Seq[Line],
  living: Living) {
  private def consoleColor = color match {
    case Red   => RED
    case White => WHITE
    case Blue  => BLUE
    case Green => GREEN 
    case Brown => YELLOW
  }
  override def toString = consoleColor + tokens.toString + Console.RESET 
}
// TODO: This method wants to be condensed to one line.
object Data {
  
  implicit val dataMonoid: Monoid[Data] = new Monoid[Data] {
    def empty = Data(
      Monoid[Color].empty,
      Monoid[Seq[Token]].empty,
      Monoid[Seq[Line]].empty,
      Monoid[Living].empty
    )
    def combine(x: Data, y: Data) = Data(
      Monoid[Color].combine(x.color, y.color), 
      Monoid[Seq[Token]].combine(x.tokens, y.tokens),
      Monoid[Seq[Line]].combine(x.lines, y.lines),
      Monoid[Living].combine(x.living, y.living)
    )
  }
}
