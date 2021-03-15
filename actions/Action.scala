package actions

import board.Board

trait Action {
  def enact: Board
}

