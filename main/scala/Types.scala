import board.{ Board }

/** An object for various types that might be used across the whole project. Some are
 *  thin aliases that only slightly aid with readability. Others (I think) are very 
 *  helpful.
 *
 *  Try to organise types in this file according to the heirachy of the project. That
 *  is, the Board and elements thereof are the most fundamental, hence first. Board 
 *  generation routines come next, then the parsing of user input and its effect on 
 *  the game state.
 */
package object Types {
  
  /** Coordinates of a square are given as a tuple (Int, Int). A region might be 
   *  referenced by pointing to a particular square it contains. This useful datatype
   *  is given an alias for readability. One day, if you really want to hurt you might
   *  try and use refined types in this project.
   */
  type XY = (Int, Int)

  type T1 = Board => Board
  type T2 = T1 => T1

  /** In natural language, when a player makes a 'move' in a game they modify the state
   *  of the board. A simple way to model this is then a function Board => Board.  This 
   *  assumes but does not actually require that the modification is small, like placing
   *  a single token, rather than large, like removing all the tokens off the board.
   */
  type Move = Board => Board

  /** Simply to make things more readable during dev. */
  type UserId = Int

}
