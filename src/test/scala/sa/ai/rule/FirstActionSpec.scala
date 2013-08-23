package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.{ActionPhase, Game}

/**
 * 29/07/13 9:02 PM
 */
class FirstActionSpec extends SpecificationWithJUnit {
  "The start of a two-player Dominion game" should {
    val firstActionState = Game.twoPlayerFirstAction

    "Have next-to-act be first player" in {
      firstActionState.nextToAct must be equalTo 0
    }

    "Be in the action phase" in {
      firstActionState.phase must be equalTo ActionPhase

      "Having moves" in {
        val firstActionMoves : Set[Move] =
          Ruleset.actions(firstActionState)

        "Consisting of only 'no action'" in {
          firstActionMoves must be equalTo Set(NoAction)
        }
      }
    }
  }
}
