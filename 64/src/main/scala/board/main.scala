package board 

import Region._
import Monoids._
import cats.Monoid
import cats.syntax.semigroup._
import monocle.Focus
import monocle.macros.syntax.all._

object Main extends App {

  val square = Square(1, 2)
  val data   = Data(Red, Tokens(List('x)))
  val r1     = Region(square :: Nil, data)

  val r2 = Region( 
    Square(1, 3) :: Square(1, 4) :: Nil, 
    Data( White, Tokens(List('o)) )
  )

  // Combining regions :)
  r1 |+| r2

  Board().checker.colorCorner.putToken.joinOnce foreach println

}
