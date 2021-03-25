package board

import monocle.Traversal
import monocle.macros.Lenses

@Lenses final case class Region(
  squares:    List[Square],
  color:      Color         = Color.White,
  lifeStatus: LifeStatus    = LifeStatus.Alive
) {
  def +(that: Region): Region = Magma[Region].combine(this, that)
  def tokens: List[Token] = squares flatMap {_.tokens}
  def edges:   List[Edge] = squares flatMap {_.edges}

  // // Spatial relations.
  // def <x(that: Region) = rcops.compareX(this, that)  < 0
  // def <=x(that: Region) = rcops.compareX(this, that) <= 0
  // def =x(that: Region) = rcops.compareX(this, that) == 0
  // def <y(that: Region) = rcops.compareY(this, that)  < 0
  // def <=y(that: Region) = rcops.compareY(this, that) <= 0
  // def =y(that: Region) = rcops.compareY(this, that) == 0

}
object Region{
  def singleton(s: Square): Region = Region(s :: Nil)
  /** With only three attributes, the handwritten regionMagma boilerplate is 
   *  not overly offensive. If it grows much more though mabye use a auto 
   *  derived one?
   */
  implicit val regionMagma: Magma[Region] = 
    Magma.instance{ (r1, r2)  => Region(
      Magma[List[Square]].combine(r1.squares, r2.squares),
      Magma[Color].combine(r1.color, r2.color),
      Magma[LifeStatus].combine(r1.lifeStatus, r2.lifeStatus)
    )}
  implicit def listMagma[T]: Magma[List[T]] = 
    Magma.instance{ (x1: List[T], x2: List[T]) => x1 ++ x2 }

  // Optics:
  val eachSquare = squares andThen Traversal.fromTraverse[List, Square]
}
