package actions


import board.{ Board, Color, Region, Transformations }

import monocle.{ Focus, Traversal }
import monocle.macros.GenLens
import monocle.macros.syntax.all._

// This is a thin interface between the frontend application and the game logic.
sealed trait Input
case class SelectSquare(xy: (Int, Int))                     extends Input
case class SelectTwoSquares(s1: (Int, Int), s2: (Int, Int)) extends Input
case class SelectToken(paramters: Any)                      extends Input
case class DragToken(parameters: Any)                       extends Input

object Action {
  
  // A specitific modification to the board state, assumed but not required to be 
  // small, e.g. remove the Monkey token from the (2, 1) square.
  type Move = Board => Board
  // Take an input of one of the types defined above and give back a move.  Hence
  // "click on square (x, y)" becomes "make the (x, y) square green.
  type SubAct[I <: Input] = I => Move
  // Take any input, and return the move.
  type Act = Input => Move


}
