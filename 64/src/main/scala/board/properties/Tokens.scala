package board 

/**
 * A Token can be one of the following: 
 *  - Rat
 *  - Elephant 
 *  - Monkey
 *  - Amoeba.
 * In each game there should be an implcit Ordering[Token] (or is that 
 * Ordered[Token] or something else?) that determines the winner in a 
 * scissors paper rock type situation, e.g. 
 * 'Elephant beats Monkey beats Rat beats Amoeba beats Elephant'
 * Note that this is not a well defined order in the usual sense.
 * Another option would be 
 * - Elephant beats (stomps on) Monkey;
 * - Monkey beats (is smarter than) Rat;
 * - Rat beats (is so small that it can climb up and annoy) Elephant;
 * - Amoeba beats everything cause macrofauna don't understand amoebas. 
 *
 * The symbol should be one of R, E , M, A corresponding to the types listed 
 * above, and the orientation an integer in 0...3 representing clockwise 
 * rotations from vertical.
 */

sealed case class Token(animal: Symbol, orientation: Int) extends Property {
  def rotate(n: Int) = Token(animal, (orientation + n) % 4)
}
object TokenOrdering extends Ordering[Token] {
  def compare(x: Token, y: Token) = ???
}
