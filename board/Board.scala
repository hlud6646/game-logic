package board

final case class Board(regions: List[Region]) extends BoardOps
object Board {
  def apply() = {
    val s = for { x <- 0 until 8; y <- 0 until 8} yield Square(x, y)
    new Board( (s map {Region.singleton(_)}).toList )
  }
  def fromChain(chain: List[Board => Board]): Board = (chain reduce {_ andThen _})(Board())
}

trait BoardOps {
  def regions:    List[Region]
}
