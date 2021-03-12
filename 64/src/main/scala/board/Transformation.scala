package board

import cats.Semigroup
import monocle.Focus
import monocle.macros.syntax.all._

import Living._


/* Routines for generating interesting initial boards, eg
 *    - a checkerboard colorscheme;
 *    - placing some initial tokens.
 *
 * These are similar in effect to 'actions' (the alteration a player makes to 
 * the board on their turn) but different enough in intent to be defined 
 * seperately.
 *
 * Note that some routines (eg checker) expect a fresh board.  Probably some
 * will break when called on a modified board (not desirable), others will do 
 * unexpected things (desirable).
 */
object Transformations {
  
  type T1 = Board => Board
  type T2 = T1 => T1

  // Transformations involving color property;
  def color(c: Color, idx: Int = 0)(b: Board) =
    b.focus(_.regions).index(idx)
     .andThen(Focus[Region](_.data.color))
     .replace(c)
  def stripes(b: Board) = 
    repeat(nTimes=32, startIndex=0, step=2)(color(Red, _))(b)

  def checker(b: Board) = colorByModulus(2, Red)(b)

  def colorByModulus(m: Int, c: Color)(b: Board) = Board(
    (b.regions zip (0 to b.regions.size))
    .map {case (r, i) => 
      if ((i + i/8)%m == 0) r
      else r.focus(_.data.color).replace(c)
  })

  // Involving joining regions;
  def join(b: Board) = {
    val (x, rest) = b.regions splitAt 2
    Board( x.reduce(Semigroup[Region].combine) :: rest  )
  }
  def joinRow(rowIndex: Int = 0)(b: Board) = {
    val (before, rest1) = b.regions.splitAt(8 * rowIndex)
    val (toJoin, rest2) = rest1.splitAt(8)
    before ::: toJoin.reduce(Semigroup[Region].combine) :: rest2
  }

  // Involving Living property.
  def killSquare(idx: Int)(b: Board) =
    b.focus(_.regions).index(idx)
     .andThen(Focus[Region](_.data.living)).replace(Dead)
  def killCenter(b: Board) = 
    Seq(27, 28, 35, 36).map{ i => killSquare(i)(_)}.reduce(_ andThen _)(b)

  // Involving Tokens
  def place(t: Token, idx: Int = 0)(b: Board) = 
    b.focus(_.regions).index(idx)
    .andThen(Focus[Region](_.data.tokens))
    .modify(_.appended(t))


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
