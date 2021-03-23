package board 

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric.Interval


object Types {

  type SquareLocation = Int Refined Interval.Closed[0, 7]
  
}
