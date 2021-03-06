package board

import cats.Semigroup
import monocle.Focus
import monocle.macros.syntax.all._


/* Routines for generating interesting initial boards, eg
 *    - a checkerboard colorscheme;
 *    - placing some initial tokens.
 * Most (all?) of the time, the board should be symmetrical across 
 * a long diagonal or the 'equator' of the board, so that both players
 * start from the same place (or nearly the same place as in chess). To
 * that end some helpers are defined which take a simple transformation 
 * and return one which preserves a particular symmetry.
 *
 * These are similar in effect to 'actions' (the alteration a player makes to 
 * the board on their turn) but different enough in intent to be defined 
 * seperately.
 */
object Transformations {
  
  type T1 = Board => Board
  type T2 = T1 => T1

  // Transformations involving color;
  def color(c: Color)(b: Board) =
    b.focus(_.regions).index(0)
     .andThen(Focus[Region](_.data.color))
     .replace(c)

  def checker(b: Board) = ???

  // Involving joining regions;
  def join(b: Board) = {
    val (x, rest) = b.regions splitAt 2
    Board( x.reduce(Semigroup[Region].combine) :: rest  )
  }
  def joinRow(rowIndex: Int = 0)(b: Board) = {
    // will be replaced with 8 everywhere soon
    val devSize = 8
    val (before, rest1) = b.regions.splitAt(devSize * rowIndex)
    val (toJoin, rest2) = rest1.splitAt(devSize)
    before ::: toJoin.reduce(Semigroup[Region].combine) :: rest2
  }

  // Diagonal, Horizontal and Vertical reflections.
  def reflectD(b: Board) = Board(b.regions.reverse)
  def reflectH(b: Board) = ???
  def reflectV(b: Board) = ???

  // 90 deg clockwise.
  def rotate(b: Board) = ???

  // Make a transformation f symmetrical across a given reflection r.
  def sym(r: T1)(f: T1) = f.andThen(r).andThen(f).andThen(r)
  def symD(f: T1) = sym(reflectD)(f)
  def symH(f: T1) = sym(reflectH)(f)
  def symV(f: T1) = sym(reflectV)(f)
}
