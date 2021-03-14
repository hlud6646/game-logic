package board

import board.Region

/** A region can contain a Line (lines) to another region (regions).
 *  The line can be dotted, as in Sprouts.
 */

final case class Line(to: Region, dotted: Boolean) extends Property
