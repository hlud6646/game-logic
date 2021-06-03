package game

import Types._
import board.{ Board, Player }
import game.Input

case class GameState(boardState: Board, toMove: Player) {
  def push(m: Move) = GameState(m(boardState), toMove.otherPlayer)
}

case class GameInvariants(p1: UserId, p2: UserId, initialBoard: Board, action: Input => Move)

case class Game(invariants: GameInvariants, state: GameState) {
  def receiveInput(i: Input) = {
    val move = invariants.action(i)
    Game(invariants, state.push(move))
  }
}


