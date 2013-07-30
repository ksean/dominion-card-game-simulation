package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.Game

/**
 * 29/07/13 9:02 PM
 */
class FirstActionSpec extends SpecificationWithJUnit {
  "First action in two-player Dominion" should {
    val firstAction = Game.twoPlayerFirstAction

    "Have next-to-act be first player" in {
      firstAction.nextToAct must be equalTo 0
    }
  }
}
