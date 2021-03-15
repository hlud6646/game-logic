package board 

/** A line is drawn from one square to another.  In some games, it might
 *  be relevant which player drew the line, and so this information is kept.
 *  A line can be dotted. This is represented as an optional Player: None
 *  means that it is not yet dotted. Some(p) means player p dotted it.
 */
final case class Edge(to: Square, dotted: Option[Player], owner: Player)
