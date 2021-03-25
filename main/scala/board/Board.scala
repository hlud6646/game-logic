package board

import monocle.Traversal
import monocle.macros.Lenses

@Lenses case class Board(regions: List[Region]) {
  // Locate a region by a square that it contains.
  def regionOf(s: Square): Region = (regions find {_.squares contains s}).get
  // Locate a region by the coordinates of a square it contains.
  def regionAt(coordinates: (Int, Int)) = {
    val (x, y) = coordinates
    (regions find { r => r.squares exists { s => s.x == x && s.y == y  } }).get
  }
}

object Board {
  // Alternate constructor providing a blank board.
  def apply() = {
    val s = for { x <- 0 until 8; y <- 0 until 8} yield Square(x, y)
    new Board( (s map {Region.singleton(_)}).toList )
  }

  // Alternate constructor from a list of board transformations (enodomorphisms).
  def fromChain(chain: List[Board => Board]): Board = (chain reduce {_ andThen _})(Board())

  // Optics:
  val eachRegion = regions andThen Traversal.fromTraverse[List, Region]
  val eachSquare = eachRegion andThen Region.eachSquare
}
