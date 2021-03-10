package board

import cats.{ Monoid, Semigroup }
import cats.syntax.semigroup._
import monocle.Focus
import monocle.macros.syntax.all._

import Data._
import Region._

case class Board(regions: List[Region]) {
  override def toString = regions.toString
  def foreach(f: Region => Unit) = regions foreach f
  def reverse = Board(regions.reverse)
  def toRenderSquares = 
    regions
    .flatMap(_.toRenderSquares)
    .sortBy(s => (s.x, s.y))
    .sliding(8, 8)
}
object Board {
  
  // main method of this module.
  def fromChain(chain: List[Board => Board]) = chain.reduce(_ andThen _)(Board())
  def apply(r: List[Region]) = new Board(r)
  // Clean board. 
  def apply() = {
    val r = for {x <- 0 until 8; y <- 0 until 8} yield Region(Square(x, y))
    new Board(r.toList)
  }
}
