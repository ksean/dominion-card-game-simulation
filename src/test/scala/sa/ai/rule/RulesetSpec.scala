package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.Game
import sa.ai.model.rule.{Shuffle, Move, Ruleset}

/**
 * Dominion rules specification
 */
class RulesetSpec extends SpecificationWithJUnit {
  "Dominion rules" should {
    val rules = Ruleset

    "Describe the two-player initial state" in {
      val initialState = Game.twoPlayerInitialState

      "With a set of first moves" in {
        val firstMoves : Set[Move] = rules.actions(initialState)

        "That contains only one option" in {
          firstMoves.size must be equalTo 1

          "Which is the first move" in {
            val firstMove : Move = firstMoves.iterator.next()

            "For the first player" in {
              firstMove.playerIndex must be equalTo 0
            }

            "Requiring a discard pile shuffle" in {
              firstMove must beAnInstanceOf[Shuffle]
            }
          }
        }
      }
    }

//    "Describe the first player shuffling the discard pile" in {
//      val shuffle = Shuffle(0)
//    }
  }
}
