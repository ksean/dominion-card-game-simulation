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
              firstMove must beAnInstanceOf[ShuffleDiscardIntoDeck]
            }
          }
        }
      }

      "Where the first player needs to shuffle" in {
        val shuffle = ShuffleDiscardIntoDeck(0)

        "After which" in {
          val afterFirstPlayerShuffle = Ruleset.transition(initialState, shuffle)

          "The first player's discard pile" in {
            val discard = afterFirstPlayerShuffle.players(0).discard

            "Is empty" in {
              discard.cards must be empty
            }
          }

          "The first player's deck" in {
            val deck = afterFirstPlayerShuffle.players(0).deck

            "Has 10 cards" in {
              deck.cards.size must be equalTo 10
            }
          }

          "The second player has not moved" in {
            afterFirstPlayerShuffle.players(1) must be equalTo initialState.players(1)
          }
        }
      }

      "Where the second player shuffles after the first player" in {
        val afterSecondPlayerShuffles : Game =
          Ruleset.transition(
            initialState,
            (0 to 1).map(ShuffleDiscardIntoDeck).toList
          )

        "Both having empty discard piles" in {
          afterSecondPlayerShuffles.players.flatMap(_.discard.cards) must be empty
        }

        "Both having the same cards in their decks that were originally discarded" in {
          afterSecondPlayerShuffles.players(1).deck.cards must containAllOf( initialState.players(1).discard.cards )
        }
      }
    }
  }
}
