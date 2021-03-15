package board

// Magma type class.
trait Magma[T] {
  def combine(x: T, y: T): T
}
object Magma {
  def apply[T](implicit mg: Magma[T]) = mg
  def instance[T](f: (T, T) => T) = new Magma[T] { def combine(x: T, y: T) = f(x, y) }
}
