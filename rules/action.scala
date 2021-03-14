package rules 

import board.{ Region, Token, Line, Board }

object Action {
  
  // An action is a function that takes as paramters at least a board, 
  // and returns an optional board.
  // Do we want callbacks or andThens?




  // Atomic actions.
  def remove(token: Token, region: Region, callback: Any = ()): Option[Board] = ???
  def    put(token: Token, region: Region, callback: Any = ()): Option[Board] = ???


  // More complex actions built from actomic ones.
  def move(token: Token, from: Region, to: Region) =
    remove(token, from, callback = put(token, to))
  def rotate(token: Token, region: Region) = 
    remove(token, region, callback = put(token.rotate(1), region))
  
  // Monkey swings along a vine.
  val monkey = Token("M", 0)
  def moveMonkey(from: Region, to: Region) =
    if (!from.data.lines.contains(Line(to, false))) None
    else remove(monkey, from, callback=put(monkey, to))




}

