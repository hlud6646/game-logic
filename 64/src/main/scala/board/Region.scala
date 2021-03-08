package board

import cats.{ Monoid, Semigroup }
import cats.syntax.semigroup._

case class Region(squares: List[Square], data: Data) {
  override def toString = s"${squares.toString}; $data"
  // crumby implementation.
  def toRenderSquares: Seq[RenderSquare] = {
    //first sqare gets all the tokens and lines.
    val h = squares.head
    val s = RenderSquare(
      h.x, 
      h.y,
      data.color,
      data.tokens,
      data.lines)
    val rest = squares.tail.map {case Square(x, y) => RenderSquare(x, y, data.color)}
    s :: rest
  }

}
object Region {
  def apply(s: Square) = new Region(s :: Nil, Monoid[Data].empty)
  implicit val regionSemigroup: Semigroup[Region] = new Semigroup[Region] {
    def combine(x: Region, y: Region) = Region(x.squares |+| y.squares, x.data |+| y.data) 
  }
}

