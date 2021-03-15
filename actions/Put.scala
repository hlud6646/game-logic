package actions

import monocle.Focus
import monocle.macros.syntax.all._

import board.{ Board, Square, Region, Token }

/** Put a token on a square.
 *  This method is a pain, which suggests an implementation 
 *  of something upstream might be very bad.
 */
case class Put(b: Board, t: Token, s: Square) extends Action {
  def apply = {
    // What is the index in b.regions of the square s?
    val i = b.regions indexWhere {_.squares contains s}
    // What is the index of this square in that regions?
    val j = b.regions(i).squares indexOf s
    b.focus(_.regions).index(i)
    .andThen(Focus[Region](_.squares).index(j))
    .andThen(Focus[Square](_.tokens)).modify(_.appended(t))
  }
}
