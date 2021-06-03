import Types._
import board._
import game._

object Main extends App {

  // Board generator gives you an intial board: 
  val initialBoard = Generator.randomBoard

  
  // Some action generating process gives you something like this.
  val action: Input => Move = {
    // Clicking a square makes it red.
    case SelectSquare(xy) => Transformations.color(xy, Color.Red)
    // Selecting two squares does nothing.
    case SelectTwoSquares(_, _) => identity
    // Dragging a token does???
    case DragToken(_) => ???
    // Clicking a token does???
    case SelectToken(_) => ???
  }

  // Mock data
  val p1 = 123
  val p2 = 456

  // We have enough now to build a game.
  val game = Game(
    GameInvariants(p1, p2, initialBoard, action),
    GameState(initialBoard, Player.P1)
  )

  // and can recieve a signal from somewhere and pass it in.
  game.receiveInput(SelectSquare((2, 4)))

  


}
