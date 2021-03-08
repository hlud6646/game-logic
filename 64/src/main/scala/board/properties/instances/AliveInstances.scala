package board

import cats.Monoid
import util.Random

object LivingInstances {

  val livingMonoid1 = new Monoid[Living] {
    def combine(x: Living, y: Living) = x match {
      case Alive => y
      case Dead  => Dead
    }
    def empty = Alive
  }

  val m = Seq(
    livingMonoid1,
  )
  implicit val livingMonoid = m(Random.nextInt(m.size))
}
