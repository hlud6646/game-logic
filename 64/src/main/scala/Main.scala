package board 

import Region._
import Transformations._
import ColorInstances._

import cats.Monoid
import cats.syntax.semigroup._
import monocle.Focus
import monocle.macros.syntax.all._

object Main extends App {

  /* Here's a small sandbox that demonstrates creating squares, regions and boards.
   * Note the syntax for combining regions (using cats semigroup syntax) and the 
   * functional style of applying transformation to a board.  Eventually, random combinations 
   * as in the last line here will produde the board.
   */

  val squares = Square(1, 2) :: Nil
  val data = Data(
    Red, 
    List(Token('A, 0)), 
    Seq[Line](), 
    Alive)
  val r1 = Region(squares, data)

  val r2 = Region( 
    Square(1, 3) :: Square(1, 4) :: Nil, 
    Data( 
      White, 
      Nil, 
      Seq[Line](), 
      Alive)
  )

  // Combining regions :)
  r1 |+| r2

  // Chaining transformations
  val b = Board.fromChain(List(
    symD(color(Red)),
    symD(join),
    place(Token('M, 0)))
  )
  





  /* A tiny sandbox for the twirl api (scala play templating engine), without the clutter
   * of a functioning play app.
   * Templates are located in src/main/twirl and are compiled to classes with an 
   * apply method, taking as arguments the content to inject into the template.
   * This, as a first step towards rendering a board.
   */
  val title = "some page"
  val greeting = "ola"
  val page: play.twirl.api.Html = html.demoTemplate(title, greeting)







  /** Test out rendering a board.
   */
  b: Board
  b.toRenderSquares foreach println

}
