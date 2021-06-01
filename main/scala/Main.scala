import actions._
import board._

object Main extends App {

  type PlayerId = Int


  case class Game(
    // Fixed.
    p1: PlayerId, 
    p2: PlayerId,
    totalAction: TotalAction,
    // Evolving.
    boardState: Board, 
    toPlay: PlayerId
    )

  // A fresh game.  The total-action and initial board chosen randomly.
  var g = Game(0, 1, Action.demoTotalAction, Board(), 0)




  // The inside of a loop like:
  // (Generalise to recieve any type of input)
  // frontend receives a signal that a player clicked the square at (2, 1)
  val input = SelectSquare(2, 1)
  // lookup what this this should do in this match
  val enactment = g.totalAction.a1.enactment(input)
  // Update game var (obv with lens or something)
  g = Game(
    g.p1, 
    g.p2,
    g.totalAction,
    enactment(g.boardState),
    Set(g.p1, g.p2).diff(Set(g.toPlay)).head
    )


  assert(g.boardState.regionAt(2, 1).color == Color.Red)
  assert(g.boardState.regionAt(2, 4).color == Color.White)
  
  /** The game data needs to be split into a match object and the existing game object. 
   *  A match contains the constant data across all games (e.g. player ids and rules)
   *  The game holds only that relevant to that game (obv),ie. board state, toPlay, legalMoveExists, result...
   */



} 

