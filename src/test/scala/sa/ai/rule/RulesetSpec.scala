package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.Game
import sa.ai.model.rule.{Move, Ruleset}

/**
 * 15/07/13 9:38 PM
 */
class RulesetSpec extends SpecificationWithJUnit {
  "Dominion rules" should {
    val rules = Ruleset

    "In the two-player initial state" in {
      val initialState = Game.twoPlayerInitialState

      "Should have a set of first moves" in {
        val firstMoves = rules.actions(initialState)

        "That contains only one option" in {
          firstMoves.size must be equalTo 1

          "This is the first move" in {
            val firstMove : Move = firstMoves.iterator.next()

            "In which first player is next to act" in {
              firstMove.playerIndex must be equalTo 0
            }
          }
        }
      }
    }
  }
}
