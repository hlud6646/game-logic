package board 
import monocle.Focus
import monocle.macros.syntax.all._

object Transformations {
  
  type T1 = Board => Board
  type T2 = T1 => T1

  
  def color(c: Color)(b: Board) = ???
}
