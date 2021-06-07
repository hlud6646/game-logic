import Types._
import board._
import game._

object Main extends App {

  // Board generator gives you an intial board: 
  val initialBoard = Generator.randomBoard
  val action = Generator.randomAction

  // Mock data
  val p1 = 123
  val p2 = 456

  // We have enough now to build a game.
  val game = Game(
    GameInvariants(p1, p2, initialBoard, action),
    GameState(initialBoard, P1)
  )

  // and can recieve a signal from somewhere and pass it in.
  game.receiveInput(SelectSquare((2, 4)))

  



  val b = Generator.randomBoard 
  def f(r: Region) = r.squares.map(s => (s, None))
  println(b.regions.flatMap(f).size)


}
