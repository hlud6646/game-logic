package actions


/** Notes: 
 *  SelectSquare can be seen as a special case of SelectTwoSquares, 
 *  where s1 == s2.  Similarly, SelectToken could be seen as a special
 *  case of DragToken.
 *
 *  This exploration is forcing the issue of whether or not the game logic
 *  really needs to be interface agnostic. That is, do we really image 
 *  people playing this game other than by clicking on squares or 
 *  click-and-dragging tokens? I don't think this particular game is 
 *  going to be amenable to a "Knight to f6" type interface. If that's the 
 *  case, then a layer of abstraction dissapears and the instance of Input 
 *  can be ClickSqaure, rather than SelectSquare as currently. If we do this, 
 *  document the design choice, don't just do it and forget the reason.
 *
 *  For consistant communication between back and front end components, each 
 *  token might need a unique ID? Example: A game might allow two identical 
 *  Monkey tokens to sit on the same square.   The front end design might 
 *  display the two tokens sitting adjacent to one another within the square.
 *  When a user moves one of them, the backend will need to know which one
 *  moved?
 * 
 *
 *
 *
 */

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


