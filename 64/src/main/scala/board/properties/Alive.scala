package board

sealed trait Living extends Property
case object Alive extends Living 
case object Dead  extends Living
