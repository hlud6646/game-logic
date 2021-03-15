package board 

sealed trait Color extends Property
object Color {
  case object Red   extends Color
  case object White extends Color

  implicit val colorMagma: Magma[Color] = Magma.instance {
    case (Red, _) => Red
    case (White, White) => White
    case (White, Red) => Red
  }
}
