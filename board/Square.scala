package board

/** As in a square on a chess board.
 */
case class Square(
  x: Int, 
  y: Int,
  tokens: Seq[Token] = Nil,
  edges:  Seq[Edge]  = Nil)
