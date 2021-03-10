package board

/** Perhaps somewhat redundant, but helpful for the following reason.
 *  Internally, a board is determined by its regions. The squares that those 
 *  regions contain is a mostly unused fact.  For the sake of rendering as html, 
 *  the attributes of every square are important.
 */
case class RenderSquare(
  x:      Int, 
  y:      Int, 
  color:  Color, 
  tokens: Seq[Token]  = Nil,
  lines:  Seq[Line]   = Nil, 
  living: Living
)
