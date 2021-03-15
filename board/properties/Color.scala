package board 

import util.Random

sealed trait Color extends Property
object Color {
  case object Red   extends Color
  case object White extends Color
 
  implicit val colorMagma: Magma[Color] = 
    Magma instance util.Random.shuffle(Combiners.all).head
  
}

object Combiners {
  import Color.{ Red, White }
  // A collection of functoins of two colors
  val all: Seq[Function2[Color, Color, Color]] = Seq(
    
    // Blend the colors.
    {
      case(Red, _)        => Red
      case(White, White)  => White 
      case(White, Red)    => Red
    },

    // Keep first.
    {
      case (x, _) => x
    },

  )


}
