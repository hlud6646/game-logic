package board

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
  implicit val instance = new FlipBehavior {
    // Have to extract the random flipper as a val, so that shuffle doesn't
    // get called every time we call apply.
    private val flipper = util.Random.shuffle(Flippers.all).head
    def apply(t: Token) = flipper(t)
  }
}

/** Define various behavious for flipping tokens.
 *  This is its own object because there are weird NPE exceptions when you
 *  ask for a random function from the list, which relies on Tokens, 
 *  which rely on an implicit FlipBehaviour which relies on ...
 */
object Flippers {
  val all: Seq[Function[Token, Token]] = Seq(
    // Identity.
    {
      case Token(x, y, z) => Token(x, y, z)
    },

    // Swap M and E, A and R, keep owner and orientation.
    {
      case Token(Some("M"), x, y) => Token(Some("E"), x, y)
      case Token(Some("E"), x, y) => Token(Some("M"), x, y)
      case Token(Some("A"), x, y) => Token(Some("R"), x, y)
      case Token(Some("R"), x, y) => Token(Some("A"), x, y)
      case Token(x, y, z) => Token(x, y, z)
    },

    // Cycle through the four in order of size.
    {
      case Token(Some("A"), x, y) => Token(Some("R"), x, y)
      case Token(Some("R"), x, y) => Token(Some("M"), x, y)
      case Token(Some("M"), x, y) => Token(Some("E"), x, y)
      case Token(Some("E"), x, y) => Token(Some("A"), x, y)
      case Token(x, y, z) => Token(x, y, z)
    },

    // Transfer ownership of traiterous Monkeys.
    {
      case Token(Some("M"), Some(player), y) => Token(Some("M"), Some(player.otherPlayer), y)
      case Token(x, y, z) => Token(x, y, z)
    },
  )
} 
