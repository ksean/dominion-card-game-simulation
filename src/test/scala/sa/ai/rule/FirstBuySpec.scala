package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.{BuyPhase, Game}

/**
 * 31/08/13 4:52 PM
 */
class FirstBuySpec extends SpecificationWithJUnit {
  "The first buy phase" should {
    val inBuyPhase = Game.twoPlayerFirstBuy
    inBuyPhase.phase must be equalTo BuyPhase

    "Have a set of moves" in {
      val movesInBuyPhase : Set[Move] =
        Ruleset.moves(inBuyPhase)

      "Where it is possible to make no buy" in {
        movesInBuyPhase must contain(NoBuy)
      }
    }
  }
}
