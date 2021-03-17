

/** Typeclass determining how regions are compared spatially.
 *  For example, we might say that A <x B for regions A and B
 *  if every square in A is to the left of every square in B.
 *  Alternatively, we might only require that at least one 
 *  square in A is to the left of at least one square in B.
 */ 
trait RegionCompareOps{
  def compareX(a: Region, b: Region): Int
  def compareY(a: Region, b: Region): Int
}

object RegionCompareOps {
  import IntSeqComparisons._
  private val ran = util.Random()
  private val f1 = ran.shuffle(comparisons).head
  private val f2 = ran.shuffle(comparisons).head
  implicit val instance = new RegionCompareOps {
    def compareX(a: Region, b: Region) = 
      f1(a.squares map {_.x}, b.squares map {_.x})
    def compareY(a: Region, b: Region) = 
      f2(a.squares map {_.y}, b.squares map {_.y})
  }
}

object IntSeqComparisons {
  val comparisons = Seq[Function3[Seq[Int], Seq[Int], Int]](
      
    // Strict
    (xs, ys) => xs forAll (x => ys.forAll (y => x < y)),

    // Relaxed
    (xs, ys) => xs exists (x => ys.exits(y => x < y)),

    // Strange
    (xs, ys) => xs exists (x => ys.forAll(y => x < y)),
    (xs, ys) => xs forAll (x => ys.exits(y => x < y)),
  )


}
