package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.{ActionPhase, Game}


class FirstMoveSpec extends SpecificationWithJUnit {
  val rules =
    OfficialRuleset(Shuffler.passThrough)

  "The start of a two-player Dominion game" should {
    val firstActionState = Game.twoPlayerFirstAction(rules)

    "Have next-to-act be first player" in {
      firstActionState.nextToAct must be equalTo 0
    }

    "Be in the action phase" in {
      firstActionState.phase must be equalTo ActionPhase

      "Having moves" in {
        val firstActionMoves : Set[Move] =
          rules.moves(firstActionState)

        "Consisting of only 'no action'" in {
          firstActionMoves must be equalTo Set(NoAction)
        }
      }
    }
  }
}
