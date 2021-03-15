package board

/** Magma typeclass. 
 *
 *  Regions are often joined together during board generation.
 *  Whatever properties each of the consituent regions has must
 *  be able to be combined to define the value of the new larger
 *  region in some way. For example, if you join a blue region
 *  with a yellow one, you might expect a green one.
 *
 *  Could have used a semigroup from cats or somewhere, but the 
 *  combine operations here are not required to be accosicative
 *  and we don't need all the cats extras for now.
 */
trait Magma[T] {
  def combine(x: T, y: T): T
}
object Magma {
  def apply[T](implicit mg: Magma[T]) = mg
  def instance[T](f: (T, T) => T) = new Magma[T] { def combine(x: T, y: T) = f(x, y) }
}
