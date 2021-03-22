package board

import org.scalacheck.{ Gen, Properties, Arbitrary }
import org.scalacheck.Prop.forAll

import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.Checkers
import org.typelevel.discipline.scalatest.FunSuiteDiscipline

object generators {
  def genAnimal = Gen.option(Gen.oneOf("A" :: "R" :: "M" :: "E" :: Nil))
  def genOwner  = Gen.option(Gen.oneOf(Player.P1, Player.P2))
  def genOrientation = Gen.choose(0, 3)
  def genToken(fb: FlipBehavior) = for {
    animal <- genAnimal
    owner  <- genOwner
    orientation <- genOrientation
  } yield Token(animal, owner, orientation)(fb)
}


/** What's the right way to deal with implicit parameters in testing?
 *  I need to set the flip behaviour globally, since even though you can 
 *  manually pass one in the token generators 
 *    val t = Token(.,.,.)(fb)
 *  t.flip will give you a token with whatever fb is in global scope, which
 *  probably isn't going to be the one you asked for.
 *  Maybe using monocle focus(...).modify(...) keeps the value of implicit parameters intact.
 *  Apparantly not.
 */
class TokenSpecs extends Properties("Token with identity flip") {
  import generators._

  val fb = new FlipBehavior { def apply(t: Token) = FlipBehavior.Flippers.identity(t) }
  implicit val arbToken = Arbitrary(genToken(fb))

  property("Rotating a token four times does nothing.") = forAll { (t: Token) =>
    t.rotate.rotate.rotate.rotate == t
  }

  property("Flipping does nothing.") = forAll { t: Token => t.flip == t }
  
}

class TokenWithSwapSpecs extends Properties("Token with swap flip") {

  val fb = new FlipBehavior { def apply(t: Token) = FlipBehavior.Flippers.swapPairs(t) }
  def genToken(fb: FlipBehavior) = for {
    owner        <- Gen.option(Gen.oneOf(Player.P1, Player.P2))
    orienntation <- Gen.choose(0, 3)
  } yield Token(Some("M"), owner, orienntation)(fb)
  implicit val arbToken = Arbitrary(genToken(fb))
  
  property("Monkey becomes Elephant") = forAll { t: Token => t.flip.animal == Some("E") }

  // property("Flipping twice does nothing") = forAll { t: Token => t.flip.flip == t }

}


class SquareSuite extends AnyFunSuite with Checkers {
  test("New square has no tokens.")(Square(1, 2).tokens.isEmpty)
}


// object SquareSuite extends ??? {
//   check("Adding and removing a token at square level does nothing") = ???
//   check("rotating a token four times at square level does nothing") = ???
//   check("adding and removing a line does nothing") = ???
// }


// and at board level...
