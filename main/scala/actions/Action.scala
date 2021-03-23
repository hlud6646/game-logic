package actions

import monocle.Focus
import monocle.macros.syntax.all._

import board.{ Board, Square, Region, Token, Edge, Player }

/** Basically a function which takes a board and some 
 *  unspecified parameters, and returns a board.
 *  To keep everything clean, these do not validate moves at all.
 */
abstract class Action {
  def enact(b: Board): Board
  def andThen(that: Action): Action = new Action {
    def enact(b: Board) =  that.enact( enact(b) )
  }
}

object Action {
  
  private def focusOnSquare(b: Board, s: Square) = {
      val i = b.regions indexWhere {_.squares contains s}
      val j = b.regions(i).squares indexOf s
      b.focus(_.regions).index(i)
      .andThen(Focus[Region](_.squares).index(j))
    }
  private def focusOnTokens(b: Board, s: Square) =
    focusOnSquare(b, s).andThen(Focus[Square](_.tokens))
  private def focusOnEdges(b: Board, s: Square) = 
    focusOnSquare(b, s).andThen(Focus[Square](_.edges))


  //  --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---  --- --- --- ---// 

  /** Put a token on a square. */
  case class Put(t: Token, s: Square) extends Action {
    def enact(b: Board) = focusOnTokens(b, s).modify(_.appended(t))
  }

  case class Remove(t: Token, s: Square) extends Action {
    def enact(b: Board) = focusOnTokens(b, s).modify(_ diff List(t))
  }

  /** Flip a token. */
  case class Flip(t: Token, s: Square) extends Action {
    def enact(b: Board) = {
      ???
      // val i = s.tokens indexOf t
      // focusOnTokens(b, s).index(i).modify(_.flip)
    }
  }
  
  /** Rotate a token. */
  case class Rotate(t: Token, s: Square) extends Action {
    def enact(b: Board) = {
      ???
      // val i = s.tokens indexOf t
      // focusOnTokens(b, s).index(i).modify(_.rotate)
    }
  }
  
  /** Connect two squares with an edge */
  case class DrawEdge(orig: Square, dest: Square, player: Player) extends Action {
    val newEdge = Edge(dest, dotted=None, owner=player)
    def enact(b: Board) = focusOnEdges(b, orig).modify(_.appended(newEdge))
  }

  /** Dot an edge. */
  case class DotAnEdge(orig: Square, edge: Edge, player: Player) extends Action {
    def enact(b: Board) = {
      val i = orig.edges indexOf edge
      focusOnEdges(b, orig).index(i).andThen(Focus[Edge](_.dotted)).replace(Some(player))
    }
  }


  //  --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---  --- --- --- ---// 

  /** Move a token from a square to another.
   */
  case class Move(t: Token, orig: Square, dest: Square) extends Action {
    def enact(b: Board) = Remove(t, orig).andThen(Put(t, dest)).enact(b)
  }

}
