package board

import org.scalacheck.{ Gen, Properties, Arbitrary }
import org.scalacheck.Prop.forAll

import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

import Player.{ P1, P2 }
import Animal.{ A, R, M, E }
import Color.{ Red, White }
import LifeStatus.{ Alive, Dead }

object Generators {
  def genOwner = Gen.oneOf(P1, P2)
  def genToken = for {
    animal      <- Gen.option(Gen.oneOf(A :: R :: M :: E :: Nil)) 
    owner       <- Gen.option(genOwner)
    orientation <- Gen.choose(0, 3)
  } yield Token(animal, owner, orientation)
  def genEdge = for {
    to      <- genSquare
    dotted  <- Gen.option(genOwner)
    owner   <- genOwner
  } yield Edge(to, dotted, owner)
  def genSquare = for {
    x <- Gen.choose(0, 7)
    y <- Gen.choose(0, 7)
    tokens <- Gen.listOf(genToken)
  } yield Square(x, y, tokens, Nil)
  def genRegion = for {
    squares <- Gen.listOf(genSquare)
    color   <- Gen.oneOf(Red, White)
    lstat   <- Gen.oneOf(Alive, Dead)
  } yield Region(squares, color, lstat)
}

class TokenSpecs extends Properties("Token with identity flip") {
  import Generators._

  implicit val arbToken = Arbitrary(genToken)

  property("Rotating a token four times does nothing.") = forAll { (t: Token) =>
    t.rotate.rotate.rotate.rotate == t
  }

  // property("Flipping does nothing.") = forAll { t: Token => t.flip == t }
  
}


class SquareSuite extends AnyFunSuite with Checkers {
  import Generators._

  test("Test init square default args.") { Square(0, 0) }
  test("Test init square args given.") {
    val t = genToken.sample.get :: Nil
    val e = genEdge.sample.get :: Nil
    Square(0, 2, t, e)
  }
  test("New square with default args has no tokens.") { Square(0, 0).tokens.isEmpty }
  test("New square with default args has no edges.") { Square(0, 1).edges.isEmpty }
}


class RegionSuite extends AnyFunSuite with Checkers {
  import Generators._
  
  test("Init region singleton.") { 
    val s = genSquare.sample.get
    val r = Region.singleton(s)
    assert(r.squares.size == 1 && r.squares.contains(s))
  }


}

class RegionSpecs extends Properties("Region") {
  import Generators._
  implicit val arbRegion: Arbitrary[Region] = Arbitrary(genRegion)

  property("If you combine two regions A and B the number of squares #(A+B) is #A + #B") =
    forAll { (a: Region, b: Region) =>
      Magma[Region].combine(a, b).squares.size == a.squares.size + b.squares.size
    }

  property("If you combine two regions, you get all their tokens") = forAll {
    (a: Region, b: Region) => Magma[Region].combine(a, b).tokens.toSet == a.tokens.toSet ++ b.tokens.toSet
  }

  property("If you combine two regions, you get all their edges") = forAll {
    (a: Region, b: Region) => Magma[Region].combine(a, b).edges.toSet == a.edges.toSet ++ b.edges.toSet
  }
}

class BoardSuite extends AnyFunSuite {

  test("Init board no args.") {
    val b = Board()
    assert(b.regions.size == 64)
    assert(b.regions.forall(_.squares.size == 1))
    assert(b.regions.forall(_.color == Color.White))
    assert(b.regions.forall(_.lifeStatus == LifeStatus.Alive))
  }

}
