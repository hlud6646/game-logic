package board

import org.scalacheck.{ Gen, Properties, Arbitrary }
import org.scalacheck.Prop.{ forAll, propBoolean }

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

  def genBoard = Gen.const(Board())

}

class TokenSpecs extends Properties("Token") {
  import Generators._
  implicit val arbToken = Arbitrary(genToken)

  property("Rotating a token four times does nothing.") = forAll { (t: Token) =>
    t.rotate.rotate.rotate.rotate == t
  }
  property("Rotating a token does something") = forAll { t: Token => t.rotate.orientation != t.orientation }
}

class TokenWithIdFlip extends Properties("Token") {
  import Generators._
  implicit val arbToken = Arbitrary(genToken)

  implicit val fb: Token => Token = identity
  property("Flipping with identity does nothing.") = forAll { t: Token => t.flip == t }
}

class TokenWithSwapFlip extends Properties("Token") {
  import Generators._
  implicit val arbToken = Arbitrary(genToken)

  implicit val fb: Token => Token = FlipBehaviour.swapPairs
  property("Flipping with swapPairs twice does nothing.") = forAll { t: Token => t.flip.flip == t }
}

class TokenWithTransferFlip extends Properties("Token") {
  import Generators._
  implicit val arbToken = Arbitrary(genToken)

  implicit val fb: Token => Token = FlipBehaviour.transfer
  property("Flipping with transfer twice does nothing.") = forAll { t: Token => t.flip.flip == t }
  property("Flipping owned monkey with transfer gives token new owner.") = forAll { t: Token => 
    (t.animal == Some(M) && t.owner.isDefined) ==> (t.flip.owner != t.owner)
  }
  property("Flipping non monkey changes nothing.") = forAll { t: Token => 
    (t.animal != Some(M)) ==> (t.flip == t)
  }
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

  // These checks are all a bit broken cause if you try to combine a region with itself, 
  // there should be no change.
  property("If you combine two regions A and B the number of squares #(A+B) is #A + #B") =
    forAll { (a: Region, b: Region) =>
      Magma[Region].combine(a, b).squares.size == a.squares.size + b.squares.size
    }

  property("If you combine two regions, you get all their tokens") = forAll {
    (a: Region, b: Region) => Magma[Region].combine(a, b).tokens.size == a.tokens.size + b.tokens.size
  }

  property("If you combine two regions, you get all their edges") = forAll {
    (a: Region, b: Region) => Magma[Region].combine(a, b).edges.size == a.edges.size + b.edges.size
  }
}

object BoardInvariants {
  implicit val arbBoard: Arbitrary[Board] = Arbitrary(Generators.genBoard)

  // All boards have 64 squares.
  forAll { b: Board => 
    (b.regions flatMap {_.squares}).size == 64  
  }

  // Any two regions either coincide or have disjoint sqaure sets.


}

class BoardSuite extends AnyFunSuite {

  test("Init board no args.") {
    val b = Board()
    assert(b.regions.size == 64)
    assert(b.regions.flatMap(_.squares).size == 64)
    assert(b.regions.forall(_.squares.size == 1))
    assert(b.regions.forall(_.color == Color.White))
    assert(b.regions.forall(_.lifeStatus == LifeStatus.Alive))
  }
}

class BoardSpecs extends Properties("Board") {
  import Generators._
  import Transformations._

  val genIndex = Gen.choose(0, 7)
  type Index = Int
  implicit val arbIndex: Arbitrary[Index] = Arbitrary(genIndex)
  implicit val arbBoard: Arbitrary[Board] = Arbitrary(genBoard)
  
  property("Joining different regions reduces length of regions list by 1") = 
    forAll { (b: Board, x1: Index, y1: Index, x2: Index, y2: Index) => 
      ( b.regionAt((x1, y1)) != b.regionAt((x2, y2)) ) ==> 
      ( join((x1, y1), (x2, y2))(b).regions.size == b.regions.size - 1 )
  }
  
  property("Joining a row on a new board takes away 7 regions. (8 becomes 1)") = 
    forAll { y: Index => joinRow(y)(Board()).regions.size == 64 - 7}

  // Note that arbitary board is not arbitrary yet, so this could be false negative.
  property("Joining a row on an arbitary board takes away up to 7 regions.") = 
    forAll { (b: Board, y: Index) =>
      joinRow(y)(b).regions.size >= b.regions.size - 7
    }

  property("Horizontal reflection is an involution") = forAll { b: Board => reflectH(reflectH(b)) == b}
  property("Vertical reflection is an involution") = forAll { b: Board => reflectV(reflectV(b)) == b}
  property("Diagonal reflection is an involution") = forAll { b: Board => reflectD(reflectD(b)) == b}
  
}
