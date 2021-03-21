package board 

/** A region might contain some properties; its color,
 *  the tokens it contains etc. Some properties are global
 *  to the region and some are derived from looking at the 
 *  squares the region contains. For example a region can 
 *  only be one color, but the tokens that belong to the 
 *  region are the sum of the tokens of each sqaure.
 */
trait Property
