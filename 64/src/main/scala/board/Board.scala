package board

import cats.{ Monoid, Semigroup }
import cats.syntax.semigroup._
import monocle.Focus
import monocle.macros.syntax.all._

import Data._
import Region._
import Transformations.{ T1, T2 }

case class Board(regions: List[Region]) {
  override def toString = regions.toString
  def foreach(f: Region => Unit) = regions foreach f
  def reverse = Board(regions.reverse)
  def sym(f: Board => Board): (Board => Board) = ??? 
  def toSquares: Vector[Vector[Square]] = ???
}
object Board {
  
  // main method of this module.
  def fromChain(chain: List[T1]) = chain.reduce((f, g) => f.andThen(g))(Board())

  // will eventually be removed and set to 8 everywhere.
  val devSize = 8
  def apply(r: List[Region]) = new Board(r)
  // Clean board. 
  def apply() = {
    val r = for {x <- 0 until devSize; y <- 0 until devSize} yield Region(Square(x, y))
    new Board(r.toList)
  }
}
