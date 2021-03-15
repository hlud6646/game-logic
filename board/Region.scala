package board

final case class Region(
  squares:    Seq[Square],
  color:      Color         = Color.White,
  lifeStatus: LifeStatus    = LifeStatus.Alive
) {
  // Attributes derived from the squares:
  def tokens: Seq[Token] = squares flatMap {_.tokens}
  def lines:  Seq[Edge]  = squares flatMap {_.edges}
}
object Region{
  def singleton(s: Square): Region = Region(s :: Nil)
  /** With only three attributes, the hand regionMagma boilerplate is 
   *  not overly offensive. If it grows much more though, use a auto 
   *  derived one with shapeless etc.
   */
  implicit val regionMagma: Magma[Region] = 
    Magma.instance{ (r1, r2)  => Region(
      Magma[Seq[Square]].combine(r1.squares, r2.squares),
      Magma[Color].combine(r1.color, r2.color),
      Magma[LifeStatus].combine(r1.lifeStatus, r2.lifeStatus)
    )}
  implicit def seqMagma[T]: Magma[Seq[T]] = 
    Magma.instance{ (x1: Seq[T], x2: Seq[T]) => x1 ++ x2 }
}
