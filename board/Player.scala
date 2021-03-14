package board

/** At this stage only serving the purpose of letting tokens be owned.
 *  Feels like one that will grow.
 */
trait Player {
  def otherPlayer: Player
}
object Player {
  case object P1 extends Player {
    def otherPlayer = P2
  }
  case object P2 extends Player {
    def otherPlayer = P1
  }
}
