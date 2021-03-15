package board

final case class Board(regions: Seq[Region]) {
  def fromChain(chain: Seq[Board => Board]): Board = (chain reduce {_ andThen _})(Board())
}
object Board {
  def apply() = {
    val s = for { x <- 0 until 8; y <- 0 until 8} yield Square(x, y)
    new Board( (s map {Region.singleton(_)}).toSeq )
  }
}
