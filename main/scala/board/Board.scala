package board

final case class Board(regions: List[Region]) {
  def regionOf(s: Square): Region = (regions find {_.squares contains s}).get
  def regionAt(coordinates: (Int, Int)) = {
    val (x, y) = coordinates
    (regions find { r => r.squares exists { s => s.x == x && s.y == y  } }).get
  }
}
object Board {
  def apply() = {
    val s = for { x <- 0 until 8; y <- 0 until 8} yield Square(x, y)
    new Board( (s map {Region.singleton(_)}).toList )
  }
  def fromChain(chain: List[Board => Board]): Board = (chain reduce {_ andThen _})(Board())
}
