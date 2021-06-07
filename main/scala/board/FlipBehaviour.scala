package board 

import monocle.Focus
import monocle.macros.syntax.all._

object FlipBehaviour {

  def swapPairs(t: Token) = t.focus(_.animal).modify( _ match {
    case Some(M) => Some(E)
    case Some(E) => Some(M)
    case Some(A) => Some(R)
    case Some(R) => Some(A)
    case None => None
  })
 
  // Transfer ownership of traiterous Monkeys.
  def transfer(t: Token) = t match {
    case Token(Some(M), Some(player), y) => Token(Some(M), Some(player.otherPlayer), y)
    case Token(x, y, z) => Token(x, y, z)
  }
}
