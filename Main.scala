import board.{ Square, Region, Board, Magma }
import board.Transformations._
import board.Color


object Main extends App {

  

  val square1 = Square(0, 0)
  val square2 = Square(0, 1)
  val square3 = Square(1, 1)

  val region1 = Region.singleton(square1)
  val region2 = Region(square2 :: square3 :: Nil)

  Magma[Region].combine(region1, region2)

  val board = Board()

  Board.fromChain(List(
    color(Color.Red, 0), 
    symD(color(Color.Red, 3)),
    repeat(nTimes=4, startIndex=10, step=3)(color(Color.Red, _))
  ))


}
