package game

import board.{ Board, Player }
import game.Input
import Types._

import monocle.macros.Lenses

@Lenses case class GameState(boardState: Board, toMove: Player) {
  def push(m: Move) = GameState(m(boardState), toMove.otherPlayer)
}

// A specific modification to the board state, assumed but not required to be 
// small, e.g. remove the Monkey token from the (2, 1) square.
case class GameInvariants(p1: UserId, p2: UserId, initialBoard: Board, action: Input => Move)

case class Game(invariants: GameInvariants, state: GameState) {
  def receiveInput(i: Input) = {
    val move = invariants.action(i)
    Game(invariants, state.push(move))
  }
}


