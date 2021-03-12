package board 

import Region._
import Transformations._
import ColorInstances._
import Living._

import cats.Monoid
import cats.syntax.semigroup._
import java.io._
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
    List(Token("A", 0)), 
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
  Board.fromChain(List(
    killCenter,
    symD(killSquare(3)),
    symD(color(Blue, 5)),
    symD(color(Red)),
    place(Token("M", 0), 2), 
    repeat(nTimes=4, startIndex=28, step=2)(place(Token("A", 0), _)),
    symD(join),
  ))


  // Fox and the hounds.
  val b = Board.fromChain(List(
    checker,
    repeat(nTimes=4, startIndex=57, step=2)(place(Token("A", 0), _)),
    place(Token("E", 0), 4),
  ))
  





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
   *  Not jumping the gun here (front end is a way off), but I'd rather look at a board
   *  than a terminal while experimenting with board creation routines.
   */
  val file = new File("./board.html")
  val bw = new BufferedWriter(new FileWriter(file))
  bw.write(html.boardTemplate(b.toRenderSquares).body)
  bw.close()







}
