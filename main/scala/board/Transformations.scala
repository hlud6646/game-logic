package board 

import Color._

import monocle.{ Focus, Traversal }
import monocle.macros.GenLens
import monocle.macros.syntax.all._

import Color._

object Transformations {
  
  type T1 = Board => Board
  type T2 = T1 => T1

<<<<<<< HEAD
  // An optic for traversing all the squares on a board.
  private val eachSquare = Board.eachRegion andThen Region.eachSquare
  private val eachColor  = Board.eachRegion andThen Region.color
  private def parity(s: Square): Int = (s.x + s.y) % 2
=======
  // Join two regions.
  def join(x: Region, y: Region): T1 =   
    _.focus(_.regions).modify(r => (x + y) :: (r diff x :: y :: Nil))
  // Join two regions specified by a representative.
  def join(x: Square, y: Square)(b: Board): Board = 
    join(b.regionOf(x), b.regionOf(y))(b)
  // Join two regions given coordinates of respective representatives.
  def join(coords1: (Int, Int), coords2: (Int, Int))(b: Board): Board =
    join( b regionAt coords1, b regionAt coords2)(b)
>>>>>>> ac0ac7c0bdc1feb9485363c0ef33e3aeb8b48d74
  
  def color(r: Region, c: Color)(b: Board): Board = 
    b.focus(_.regions)
     .index( b.regions indexOf r )
     .andThen(Focus[Region](_.color))
     .replace(c)
  def color(s: Square, c: Color)(b: Board): Board = 
    color(b regionOf s, c)(b)
  def color(coordinates: (Int, Int), c: Color)(b: Board): Board = 
    color(b regionAt coordinates, c)(b)

  private def colorByPredicate(p: Region => Boolean, c: Color): T1 = 
    Board.eachRegion.modify(r =>  if (p(r)) r else  r.focus(_.color).replace(c))
  def checker: T1 = {
    def parity(s: Square) = (s.x + s.y) % 2 ==  0
    colorByPredicate( r => parity(r.squares.head), Red )
  }
  def stripes: T1 = colorByPredicate( r => r.squares.head.y % 2   == 0, Red )

<<<<<<< HEAD
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
=======
  def joinRow(y: Int): T1 = {
>>>>>>> ac0ac7c0bdc1feb9485363c0ef33e3aeb8b48d74
    val joins = (1 to 7) map { x => join((0, y), (x, y))(_) }
    (joins reduce (_ andThen _))
  }
  def joinColumn(x: Int): T1 = {
    val ops: Seq[T1] = Seq( rotate, joinRow(7 - x), rotate, rotate, rotate )
    ops reduce { _ andThen _ }
  }
  // Diagonal, Horizontal and Vertical reflections.
  private def reflectOrthogonal(l: monocle.Lens[Square, Int]): T1 = {
    val traversal = Board.eachSquare andThen l
    def f(z: Int) = (7 - z) % 8
    traversal.modify(f)
  }
  def reflectH: T1 = reflectOrthogonal(Square.x)
  def reflectV: T1 = reflectOrthogonal(Square.y)
  def reflectD: T1 = reflectV andThen reflectH
  
  // 90 deg clockwise.
  def rotate: T1 = Board.eachSquare.modify( 
    { case Square(x, y, tokens, edges) => Square(y, 7 - x, tokens, edges) }
  )

  // Make a transformation symmetrical across an axis.
  def sym(r: T1)(f: T1) = f.andThen(r).andThen(f).andThen(r)
  def symD(f: T1) = sym(reflectD)(f)
  def symH(f: T1) = sym(reflectH)(f)
  def symV(f: T1) = sym(reflectV)(f)
  
  // Repeat a transformation that takes one integer parameter.
  def repeat(nTimes: Int, startIndex: Int, step: Int)(f: Int => T1) =
    (0 until nTimes) map {_ * step + startIndex} map f reduce {_ andThen _}
}
