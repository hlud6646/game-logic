package board

/** As in a square on a chess board.
 */
case class Square(
  x: Int, 
  y: Int,
  tokens: List[Token] = Nil,
  edges:  List[Edge]  = Nil)
