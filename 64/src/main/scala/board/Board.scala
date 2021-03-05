package board

import cats.{ Monoid, Semigroup }
import cats.syntax.semigroup._
import monocle.Focus
import monocle.macros.syntax.all._

import Data._
import Region._
import Monoids._

case class Board(regions: List[Region]) {
  // Utils
  override def toString = regions.toString
  def foreach(f: Region => Unit) = regions foreach f
  def reverse = Board(regions.reverse)
  def sym(f: Board => Board): (Board => Board) = ??? 

  // Transformations.
  def colorCorner = this.focus(_.regions)
    .index(0)
    .andThen(Focus[Region](_.data.color))
    .replace(Red)

  def checker = Board(
    regions.zip(0 until regions.size) map { case (r, i) => 
      if (i % 2 == 0) r else r.focus(_.data.color).replace(Red)
    }
  )

  def putToken = 
    this.focus(_.regions).index(0)
    .andThen(Focus[Region](_.data.tokens))
    .modify(_.add('x))

  def joinOnce = {
    val (x, rest) = regions.splitAt(2)
    Board( x.reduce(Semigroup[Region].combine) :: rest )
  }

  /* Although the state of the board is most logically stored internally by region, 
   * for the sake of actually rendering the board in html there must be a grid of 
   * squares.
   */
  def toSquares = Vector[Vector[Square]] = ???
}
object Board {
  // will eventually be removed and set to 8 everywhere.
  val N = 3
  def apply(r: List[Region]) = new Board(r)
  // Clean board. 
  def apply() = {
    val r = for {x <- 1 to N; y <- 1 to N} yield Region(Square(x, y))
    new Board(r.toList)
  }
}
