import util.Random.{ nextInt, nextBoolean }

import Types._
import board._
import Transformations._
import game._
/** Probably the only genuinely interesting part of this project.
 *  An initial board, actions affecting the board and a win condition
 *  are chosen.  Mostly placeholder implementations though at this point.
 */
package object Generator {

  def randomBoard = {
    def oneOf[T](x: List[T]): T = x(nextInt(x.size))
    def randomCoord = (nextInt(8), nextInt(8))

    // Start with a board coloured somehow
    val colorMethods: List[T1] = List( checker, stripes, identity )
    
    // Then get a list of joins: jn: An join Bn 
    def randomJoin: T1 = join(randomCoord, randomCoord)
    // Expected number of joins is?
    def joins: List[T1] = if (nextInt(4) > 0) (randomJoin :: joins) else Nil

    // Then give the joins some symmetry:
    val syms: List[T2] = List(symV, symH, symD)
    def joins2 = joins map { oneOf(syms)(_) }
   
    // Splash some tokens around.
    val randomTokenPlacement: T1 = dropMonkey(randomCoord)
    def tokenPlacements: List[T1] = if(nextInt(5) > 0) (randomTokenPlacement :: tokenPlacements) else Nil

    // Give the token placements some symmetry:
    val tps = tokenPlacements map { oneOf(syms)(_) }

    val chain: List[T1] = oneOf(colorMethods) :: joins2 ::: tps
    Board.fromChain(chain)
  }



  // Some action generating process gives you something like this.
  def randomAction: Input => Move = {
    // Clicking a square makes it red.
    case SelectSquare(xy) => Transformations.color(xy, Color.Red)
    // Selecting two squares does nothing.
    case SelectTwoSquares(_, _) => identity
    // Dragging a token does???
    case DragToken(_) => ???
    // Clicking a token does???
    case SelectToken(_) => ???
  }
}
