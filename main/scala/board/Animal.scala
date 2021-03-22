package board 

sealed trait Animal
object Animal {
  case object A extends Animal
  case object R extends Animal
  case object M extends Animal
  case object E extends Animal
}
