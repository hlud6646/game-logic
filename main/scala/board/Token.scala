package board

/** A token placed on a square, as in a chess piece or a disk in checkers.
 *  Animal can only be "E" for elephant, "R" for Rat "M" for Monkey or "A" for Amoeba.
 */

case class Token(
  animal:         Option[Animal],
  owner:          Option[Player],
  orientation:    Int)(
  implicit fb:    FlipBehavior) {
    def flip   = fb.apply(this)
    def rotate = Token(animal, owner, (orientation + 1) % 4) 
  }

