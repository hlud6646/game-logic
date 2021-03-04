package board

import cats.{ Monoid, Semigroup }
import cats.syntax.semigroup._

case class Region(squares: List[Square], data: Data) {
  override def toString = s"${squares.toString}; $data"
}
object Region {
  // Not needed since Region is a case class now.
  // def apply(s: List[Square], d: Data) = new Region(s, d)
  def apply(s: Square) = new Region(s :: Nil, Monoid[Data].empty)
  implicit val regionSemigroup: Semigroup[Region] = new Semigroup[Region] {
    def combine(x: Region, y: Region) = Region(x.squares |+| y.squares, x.data |+| y.data) 
  }
}

