package board

sealed trait Living extends Property
final case object Alive extends Living 
final case object Dead  extends Living
