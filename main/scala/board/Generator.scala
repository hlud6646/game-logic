package board

import util.Random.{ nextInt, nextBoolean }

import Transformations._

object Generator extends App {

  // 1st attempt at board generation.
  
  def oneOf[T](x: List[T]): T = x(nextInt(x.size))
  def randomCoord = (nextInt(8), nextInt(8))

  // Start with a board coloured somehow
  val colorMethods: List[T1] = List( checker, stripes, identity )
  
  // Then get a list of joins: jn: An join Bn 
  def randomJoin: T1 = join(randomCoord, randomCoord)
  // Expected number of joins is?
  def joins: List[T1] = if (nextInt(4) > 0) (randomJoin :: joins) else Nil

  // Then give the joins some symmetry:
  val syms: List[T2] = List(symV, symH, symD, identity)
  val joins2 = joins map { oneOf(syms)(_) }
 
  // Splash some tokens around (dummy fn body)
  def randomTokenPlacement: T1 = identity
  def tokenPlacements: List[T1] = if(nextInt(5) > 0) (randomTokenPlacement :: tokenPlacements) else Nil

  // Give the token placements some symmetry:
  def tps = tokenPlacements map { oneOf(syms)(_) }

  val chain: List[T1] = oneOf(colorMethods) :: joins2 ::: tps

  val b = Board.fromChain(chain)


}
