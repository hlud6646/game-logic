import board.{ Square, Region, Magma }


object Main extends App {

  

  val square1 = Square(0, 0)
  val square2 = Square(0, 1)
  val square3 = Square(1, 1)

  val region1 = Region.singleton(square1)
  val region2 = Region(square2 :: square3 :: Nil)

  Magma[Region].combine(region1, region2)


}
