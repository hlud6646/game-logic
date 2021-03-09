package rules 

import board.{ Region, Token, Line }

object Action {
  
  // Not sure yet how to link these to an actual board. At this point 
  // it looks like they are side effecting funtions, acting on a mutable 
  // board object.





  // Atomic actions.
  def remove(token: Token, region: Region, callback: Any = ()): Unit = ???
  def    put(token: Token, region: Region, callback: Any = ()): Unit = ???


  // More complex actions built from actomic ones.
  def move(token: Token, from: Region, to: Region): Unit =
    remove(token, from, callback = put(token, to))
  def rotate(token: Token, region: Region) = 
    remove(token, region, callback = put(token.rotate(1), region))
  
  // Monkey swings along a vine.
  val monkey = Token('M, 0)
  def moveMonkey(from: Region, to: Region) = 
    if (!from.data.lines.contains(Line(to, false))) throw new Exception("no vine for monkey")
    else remove(monkey, from, callback=put(monkey, to))




}

