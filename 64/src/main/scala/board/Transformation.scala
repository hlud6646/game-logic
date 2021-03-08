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
 *
 * Note that some routines (eg checker) expect a fresh board.  Probably some
 * will break when called on a modified board (not desirable), others will do 
 * unexpected things (desirable).
 */
object Transformations {
  
  type T1 = Board => Board
  type T2 = T1 => T1

  // Transformations involving color property;
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
    val (before, rest1) = b.regions.splitAt(8 * rowIndex)
    val (toJoin, rest2) = rest1.splitAt(8)
    before ::: toJoin.reduce(Semigroup[Region].combine) :: rest2
  }

  // Involving Living property.
  def killSquare(b: Board) = ???
  def killCenter(b: Board) = ???

  // Involving Tokens
  def place(t: Token)(b: Board) = 
    b.focus(_.regions).index(1)
    .andThen(Focus[Region](_.data.tokens))
    .modify(_.appended(t))

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
