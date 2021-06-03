package board

import util.Random.{ nextInt, nextBoolean }

import Transformations._

object Generator {

  // 1st attempt at board generation.
  
  private def oneOf[T](x: List[T]): T = x(nextInt(x.size))
  private def randomCoord = (nextInt(8), nextInt(8))

  // Start with a board coloured somehow
  private def colorMethods: List[T1] = List( checker, stripes, identity )
  
  // Then get a list of joins: jn: An join Bn 
  private def randomJoin: T1 = join(randomCoord, randomCoord)
  // Expected number of joins is?
  private def joins: List[T1] = if (nextInt(4) > 0) (randomJoin :: joins) else Nil

  // Then give the joins some symmetry:
  private def syms: List[T2] = List(symV, symH, symD, identity)
  private def joins2 = joins map { oneOf(syms)(_) }
 
  // Splash some tokens around (dummy fn body)
  private def randomTokenPlacement: T1 = identity
  private def tokenPlacements: List[T1] = if(nextInt(5) > 0) (randomTokenPlacement :: tokenPlacements) else Nil

  // Give the token placements some symmetry:
  private def tps = tokenPlacements map { oneOf(syms)(_) }

  private def chain: List[T1] = oneOf(colorMethods) :: joins2 ::: tps

  def randomBoard = Board.fromChain(chain)


}
