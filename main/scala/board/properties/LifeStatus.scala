package board

trait LifeStatus extends Property
object LifeStatus {
  case object Alive extends LifeStatus
  case object Dead  extends LifeStatus
  
  implicit val lifeStatusMagma: Magma[LifeStatus] = Magma.instance {
    case (Dead, _)      => Dead
    case (Alive, Dead)  => Dead
    case (Alive, Alive) => Alive
  }
}
