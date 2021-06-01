package board

import monocle.macros.Lenses

/** As in a square on a chess board.
 */
@Lenses case class Square(
  x: Int, 
  y: Int
)

    ////Positional comparisons.
    //def <x  (that: Square) = this.x <x that.x
    //def =x  (that: Square) = this.x =x that.x
    //def <=x (that: Square) = <x (that) || =x (that)
    //def >x  (that: Square) = !<=x (that)
    //def >=x (that: Square) = !<x (that)
    //def <y  (that: Square) = this.y <y that.y
    //def =y  (that: Square) = this.y =y that.y
    //def <=y (that: Square) = <y (that) || =y (that)
    //def >y  (that: Square) = !<=y (that)
    //def >=y (that: Square) = !<y (that)


