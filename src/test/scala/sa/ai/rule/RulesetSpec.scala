package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.Game
import sa.ai.model.card.Card

/**
 * Dominion rules specification
 */
class RulesetSpec extends SpecificationWithJUnit {
  "Dominion rules for two players" should {
    val rules = Ruleset

    val initialState = Game.twoPlayerInitialState
    "Describe the initial state" in {
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
    }


    val afterSecondPlayerShuffles : Game =
      Ruleset.transition(
        initialState,
        (0 to 1).map(ShuffleDiscardIntoDeck).toList
      )
    "Start by both players shuffling" in {
      "Where the second player shuffles after the first player" in {
        "Both having empty discard piles" in {
          afterSecondPlayerShuffles.players.flatMap(_.discard.cards) must be empty
        }

        "Both having the same cards in their decks that were originally discarded" in {
          // todo: test for both players (instead of just 2nd player)
          afterSecondPlayerShuffles.players(1).deck.cards must containAllOf( initialState.players(1).discard.cards )
        }

        "Both having an empty hand" in {
          afterSecondPlayerShuffles.players.flatMap(_.hand.cards) must be empty
        }
      }
    }

    "Continues by both players drawing cards" in {
      val afterBothPlayersDraw =
        Ruleset.transition(
          afterSecondPlayerShuffles,
          (0 to 1).map(DrawFromDeck.initialHand).toList
        )

      "From their deck" in {
        foreach( afterBothPlayersDraw.players ) {
          _.deck.cards must be empty
        }
      }

      "Into their hand" in {
        foreach( afterBothPlayersDraw.players ) {
          _.hand.cards must have size 5
        }
      }
    }
  }
}
