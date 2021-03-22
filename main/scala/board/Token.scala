package board

/** A token placed on a square, as in a chess piece or a disk in checkers.
 *  Animal can only be "E" for elephant, "R" for Rat "M" for Monkey or "A" for Amoeba.
 *  Should we use a refined type here or some other implementation? Using string 
 *  when only four single character values are allowed seems smelly.
 *
 *  Since the animal is actually only allowed to be one of four values, 
 *  String is not the right type. 
 *  TODO: Migrate to the adt defined below.
 */

case class Token(
  animal:         Option[Animal],
  owner:          Option[Player],
  orientation:    Int)(
  implicit fb:    FlipBehavior) {
    def flip   = fb.apply(this)
    def rotate = Token(animal, owner, (orientation + 1) % 4) 
  }

