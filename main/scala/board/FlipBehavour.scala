package board

import monocle.Focus
import monocle.macros.syntax.all._

/** What is this!?
 *
 * Tokens can be flipped. When they are, something might happen.
 * This behaviour should be chosen once at the start of the match and left 
 * alone.  For that reason the logic must be kept seperate from any Token
 * constructor.
 *
 * This object contains a list of various (sort of) logical things that could
 * happen when a player flips a token. An implicit 'flip behaviour' value 
 * is defined, which globally determines what happens when a token is flipped.
 */
trait FlipBehavior {
  def apply(t: Token): Token
}

object FlipBehavior {
  import Flippers._ 
  val all: Seq[Function[Token, Token]] = Seq(
    identity, swapPairs, cycle, transfer)
  implicit val instance = new FlipBehavior {
    // Have to extract the random flipper as a val, so that shuffle doesn't
    // get called every time we call apply.
    val flipper = util.Random.shuffle(all).head
    def apply(t: Token) = flipper(t)
  }


  /** Define various behavious for flipping tokens.
   *  This is its own object because there are weird NPE exceptions when you
   *  ask for a random function from the list, which relies on Tokens, 
   *  which rely on an implicit FlipBehaviour which relies on ...
   */
  object Flippers {
    // Identity.
    val identity: Function[Token, Token] = {
      case Token(x, y, z) => Token(x, y, z)
    }

    def swapPairs(t: Token) = t.focus(_.animal).modify( _ match {
      case Some("M") => Some("E")
      case Some("E") => Some("M")
      case Some("A") => Some("R")
      case Some("R") => Some("A")
      case None => None
    })

    // Cycle through the four in order of size.
    def cycle(t: Token) = t.focus(_.animal).modify(_ match {
      case Some("A") => Some("R")
      case Some("R") => Some("M")
      case Some("M") => Some("E")
      case Some("E") => Some("A")
      case None => None
    })

    // Transfer ownership of traiterous Monkeys.
    val transfer: Function[Token, Token] = {
      case Token(Some("M"), Some(player), y) => Token(Some("M"), Some(player.otherPlayer), y)
      case Token(x, y, z) => Token(x, y, z)
    }
  
  } 
}
