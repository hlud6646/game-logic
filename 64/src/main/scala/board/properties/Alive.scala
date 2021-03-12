package board

sealed trait Living extends Property
object Living {
  case object Alive extends Living 
  case object Dead  extends Living
}
