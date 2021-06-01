package actions

import board.{ Board, Color, Region, Transformations }

import monocle.{ Focus, Traversal }
import monocle.macros.GenLens
import monocle.macros.syntax.all._

// This is a thin interface between the frontend application and the game logic.
trait Input

case class SelectSquare(xy: (Int, Int))                     extends Input
case class SelectTwoSquares(s1: (Int, Int), s2: (Int, Int)) extends Input
case class SelectToken(paramters: Any)                      extends Input
case class DragToken(parameters: Any)                       extends Input

// Define the response to a particular type of input.
case class Action[I <: Input](enactment: I => Board => Board)

// Define the response to all types of inputs.
case class TotalAction(
  a1: Action[SelectSquare],
  a2: Action[SelectTwoSquares],
  a3: Action[SelectToken],
  a4: Action[DragToken]
)




// Some examples.
object Action {

  val makeRegionRed = Action[SelectSquare]({
    case SelectSquare(xy) => (b: Board) => Transformations.color(xy, Color.Red)(b)
  })

  val joinTwoRegion = Action[SelectTwoSquares]({
    case SelectTwoSquares(s1, s2) => (b: Board) => Transformations.join(s1, s2)(b)
  })

  val selectTokenId = Action[SelectToken]({ _ => identity })
  val dragTokenId = Action[DragToken]({ _ => identity })

  val demoTotalAction = TotalAction(makeRegionRed, joinTwoRegion, selectTokenId, dragTokenId)

  

}


