package game

import Types._

// This is a thin interface between the frontend application and the game logic.
sealed trait Input
case class SelectSquare(xy: XY)             extends Input
case class SelectTwoSquares(s1: XY, s2: XY) extends Input
case class SelectToken(paramters: Any)      extends Input
case class DragToken(parameters: Any)       extends Input
