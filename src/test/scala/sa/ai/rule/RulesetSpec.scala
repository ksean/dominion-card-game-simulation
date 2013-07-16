package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.Game

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

      "Where the first player needs to shuffle" in {
        val shuffle = Shuffle(0)

        "After which" in {
          val postShuffle = Ruleset.transition(initialState, shuffle)

          "The first discard pile" in {
            val discard = postShuffle.players(0).discard

            "Is empty" in {
              discard.cards must be empty
            }
          }

//          "The deck" in {
//            postShuffle.de
//          }
        }
      }
    }
  }
}
