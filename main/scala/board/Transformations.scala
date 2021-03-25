package board 

import monocle.{ Focus, Traversal }
import monocle.macros.GenLens
import monocle.macros.syntax.all._

object Transformations {
  
  type T1 = Board => Board
  type T2 = T1 => T1
  
  // /** Color a region.
  //  *  @param c the color applied to the region.
  //  *  @param idx the index of the region to color.
  //  *  @return a new board with the region updated.
  //  */
  // def color(c: Color, idx: Int)(b: Board) = 
  //   b.focus(_.regions).index(idx).andThen(Focus[Region](_.color)).replace(c)
  // def stripes(c: Color)(b: Board) = 
  //   repeat(nTimes=32, startIndex=0, step=2)(color(c, _))(b)
  // def checker(c: Color)(b: Board) = colorByModulus(2, c)(b)
  // def colorByModulus(m: Int, c: Color)(b: Board) = Board(
  //   (b.regions zip (0 to b.regions.size))
  //   .map {case (r, i) => 
  //     if ((i + i/8)%m == 0) r
  //     else r.focus(_.color).replace(c)
  // })
  
  def color(r: Region, c: Color)(b: Board): Board = 
    b.focus(_.regions)
     .index( b.regions indexOf r )
     .andThen(Focus[Region](_.color))
     .replace(c)
  def color(s: Square, c: Color)(b: Board): Board = 
    color(b regionOf s, c)(b)
  def color(coordinates: (Int, Int), c: Color)(b: Board): Board = 
    color(b regionAt coordinates, c)(b)
  
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
    val traversal = Board.eachRegion andThen Region.eachSquare andThen l
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
