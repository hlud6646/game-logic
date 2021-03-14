package board

sealed trait Color extends Property
case object White extends Color
case object Red   extends Color
case object Green extends Color
case object Blue  extends Color
case object Brown extends Color