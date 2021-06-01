package board 

import Color._

import monocle.{ Focus, Traversal }
import monocle.macros.GenLens
import monocle.macros.syntax.all._

/** Modifications to the state of the board are contained here.
 *  Note that none of the classes for smaller parts (region or square)
 *  contain modification methods.  They do provide optics which allow
 *  a function Board => Board to modify one/many of these nested parts.
 */
object Transformations {
  
  type T1 = Board => Board
  type T2 = T1 => T1

  // An optic for traversing all the squares on a board.
  private val eachSquare = Board.eachRegion andThen Region.eachSquare
  private val eachColor  = Board.eachRegion andThen Region.color
  private def parity(s: Square): Int = (s.x + s.y) % 2
  
  def color(r: Region, c: Color)(b: Board): Board = 
    b.focus(_.regions)
     .index( b.regions indexOf r )
     .andThen(Focus[Region](_.color))
     .replace(c)
  def color(s: Square, c: Color)(b: Board): Board = 
    color(b regionOf s, c)(b)
  def color(coordinates: (Int, Int), c: Color)(b: Board): Board = 
    color(b regionAt coordinates, c)(b)


  // Checkerboard pattern.  No idea what will happen if the board contains previous joins.
  private def colorByPredicate(p: Region => Boolean, c: Color)(b: Board): Board =
    Board.eachRegion.modify(r =>  if (p(r)) r else  r.focus(_.color).replace(c))(b)
  def checker(b: Board) = colorByPredicate( r => parity(r.squares.head) == 0, Red )(b)
  def stripes(b: Board) = colorByPredicate( r => r.squares.head.y % 2   == 0, Red )(b)
  
  // Join two regions.
  def join(x: Region, y: Region)(b: Board): Board =   
    b.focus(_.regions).modify(r => (x + y) :: (r diff x :: y :: Nil))
  // Join two regions specified by a representative.
  def join(x: Square, y: Square)(b: Board): Board = 
    join(b.regionOf(x), b.regionOf(y))(b)
  def join(coords1: (Int, Int), coords2: (Int, Int))(b: Board): Board =
    join( b regionAt coords1, b regionAt coords2)(b)


  def joinRow(y: Int)(b: Board) = {
    val joins = (1 to 7) map { x => join((0, y), (x, y))(_) }
    (joins reduce (_ andThen _))(b)
  }
  
  // Diagonal, Horizontal and Vertical reflections.
  private def reflectOrthogonal(l: monocle.Lens[Square, Int])(b: Board) = {
    val traversal = eachSquare andThen l
    def f(z: Int) = (7 - z) % 8
    traversal.modify(f)(b)
  }
  def reflectH(b: Board): Board = reflectOrthogonal(Square.x)(b)
  def reflectV(b: Board): Board = reflectOrthogonal(Square.y)(b)
  def reflectD(b: Board): Board = reflectV(reflectH(b))
  
  // 90 deg clockwise.
  def rotate(b: Board) = ???

  // Make a transformation symmetrical across an axis.
  def sym(r: T1)(f: T1) = f.andThen(r).andThen(f).andThen(r)
  def symD(f: T1) = sym(reflectD)(f)
  def symH(f: T1) = sym(reflectH)(f)
  def symV(f: T1) = sym(reflectV)(f)
  
  // Repeat a transformation that takes one integer parameter.
  def repeat(nTimes: Int, startIndex: Int, step: Int)(f: Int => T1) =
    (0 until nTimes) map {_ * step + startIndex} map f reduce {_ andThen _}
}
