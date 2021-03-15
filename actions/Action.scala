package actions

import board.Board

trait Action {
  def apply: Board
}

