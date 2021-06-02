import board.{ Board, Player }
import actions.Action._
import actions.Input

object Main extends App {

  // @Lenses annotation here.
  case class GameState(boardState: Board, toMove: Player) {
    def push(m: Move) = GameState(m(boardState), toMove.otherPlayer)
  }

  // Act is not well named. It is the function Input => Move which declares what effect 
  // an input should have on the game state.
  type UserId = Int
  case class GameInvariants(p1: UserId, p2: UserId, initialBoard: Board, act: Act)

  case class Game(invariants: GameInvariants, state: GameState) {
    def handleInput(i: Input) = {
      val move = invariants.act(i)
      Game(invariants, state.push(move))
    }
  }
}
