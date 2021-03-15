package board 

import monocle.Focus
import monocle.macros.syntax.all._

object Transformations {
  
  type T1 = Board => Board
  type T2 = T1 => T1
  
  /** Color a square.
   *  @param c the color applied to the square.
   *  @param idx the index of the square to color.
   *  @return a new square with the color updated.
   */
  def color(c: Color, idx: Int)(b: Board) = 
    b.focus(_.regions).index(idx).andThen(Focus[Region](_.color)).replace(c)
  def stripes(c: Color)(b: Board) = 
    repeat(nTimes=32, startIndex=0, step=2)(color(c, _))(b)
  def checker(c: Color)(b: Board) = colorByModulus(2, c)(b)
  def colorByModulus(m: Int, c: Color)(b: Board) = Board(
    (b.regions zip (0 to b.regions.size))
    .map {case (r, i) => 
      if ((i + i/8)%m == 0) r
      else r.focus(_.color).replace(c)
  })

  
  def join(b: Board) = {
    val (x, rest) = b.regions splitAt 2
    Board( x.reduce(Magma[Region].combine) :: rest  )
  }
  def joinRow(rowIndex: Int = 0)(b: Board) = {
    val (before, rest1) = b.regions.splitAt(8 * rowIndex)
    val (toJoin, rest2) = rest1.splitAt(8)
    before ::: toJoin.reduce(Magma[Region].combine) :: rest2
  }





  // Diagonal, Horizontal and Vertical reflections.
  def reverse(b: Board)  = Board(b.regions.reverse)
  def reflectD(b: Board) = reverse(b)
  def reflectH(b: Board) = ???
  def reflectV(b: Board) = ???

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
