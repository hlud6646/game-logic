package board

import cats.{ Monoid, Semigroup }
import cats.syntax.semigroup._

import ColorInstances._

// A region must contain at least one square. Try playing with refined types later.
case class Region(
  squares:    List[Square],
  color:      Color                   = White,
  tokens:     Map[Square, Seq[Token]] = Map(),
  lines:      Map[Square, Seq[Line]]  = Map(),
  living:     Living                  = Living.Alive,
  ){
  override def toString = s"${squares.toString}; "
  // crumby implementation.
  def toRenderSquares: Seq[RenderSquare] = {
    //first sqare gets all the tokens and lines.
    val h = squares.head
    val s = RenderSquare(
      h.x, 
      h.y,
      color,
      tokens.values.reduce(_ ++ _),
       lines.values.reduce(_ ++ _), 
      living)
    val rest = squares.tail.map {case Square(x, y) => RenderSquare(
      x, 
      y, 
      color, 
      Nil,
      List[Line](),
      living)}
    s :: rest
  }

}
object Region {
  def apply(s: Square) = new Region(
    s :: Nil)
  implicit val regionSemigroup: Semigroup[Region] = new Semigroup[Region] {
    def combine(x: Region, y: Region) = Region(
      x.squares   |+| y.squares, 
      // x.data      |+| y.data,
      x.color     |+| y.color) 
  }
}

