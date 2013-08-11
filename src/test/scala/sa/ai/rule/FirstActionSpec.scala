package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.Game

/**
 * 29/07/13 9:02 PM
 */
class FirstActionSpec extends SpecificationWithJUnit {
  "At the start of a two-player Dominion game" should {
    val firstAction = Game.twoPlayerFirstAction

    "Have next-to-act be first player" in {
      firstAction.nextToAct must be equalTo 0
    }

    "With moves" in {
      val firstActionMoves : Set[Move] =
        Ruleset.actions(firstAction)

      "Consisting of only no action" in {
        firstActionMoves must be equalTo Set(NoAction)
      }
    }
  }
}
