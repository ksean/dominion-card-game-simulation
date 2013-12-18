package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.{BeforeTheGamePhase, Game}

/**
 * Dominion game setup rules specification
 */
class BeforeTheGameSpec extends SpecificationWithJUnit {
  "Dominion rules for two players" should {
    val rules = OfficialRuleset()

    val initialState = Game.twoPlayerInitialState
    "Describe the initial state" in {

      "Before the game starts" in {
        initialState.phase must be equalTo BeforeTheGamePhase
      }

      "With a set of first moves" in {
        val firstMoves : Set[Move] = rules.moves(initialState)

        "That contains only one option" in {
          firstMoves.size must be equalTo 1

          "Which is the first move" in {
            val firstMove : Move = firstMoves.iterator.next()

            "For the first player" in {
              initialState.nextToAct must be equalTo 0
            }

            "Requiring a discard pile shuffle" in {
              firstMove must be equalTo ShuffleDiscardIntoDeck
            }
          }
        }
      }

      "Where the first player needs to shuffle" in {
        val shuffle = ShuffleDiscardIntoDeck

        "After which" in {
          val afterFirstPlayerShuffle = rules.transition(initialState, shuffle)

          "The first player's discard pile" in {
            val discard = afterFirstPlayerShuffle.players(0).discard

            "Is empty" in {
              discard.cards must beEmpty
            }
          }

          "The first player's deck" in {
            val deck = afterFirstPlayerShuffle.players(0).deck

            "Has 10 cards" in {
              deck.cards.size must be equalTo 10
            }
          }

          "The second player needs to shuffle" in {
            afterFirstPlayerShuffle.nextToAct must be equalTo 1
            rules.moves(afterFirstPlayerShuffle) must be equalTo Set(shuffle)
          }

          "The second player has not moved" in {
            afterFirstPlayerShuffle.players(1) must be equalTo initialState.players(1)
          }
        }
      }
    }


    val afterSecondPlayerShuffles : Game =
      rules.transition(
        initialState,
        List.fill(2)(ShuffleDiscardIntoDeck)
      )
    "Start by both players shuffling" in {
      "Where the second player shuffles after the first player" in {
        "Both having empty discard piles" in {
          afterSecondPlayerShuffles.players.flatMap(_.discard.cards) must beEmpty
        }

        "Both having the same cards in their decks that were originally discarded" in {
          foreach( 0 to 1 ) { playerIndex =>
            val discardBefore = initialState.players(playerIndex).discard.cards
            val deckAfter = afterSecondPlayerShuffles.players(playerIndex).deck.cards

            discardBefore.size must be equalTo deckAfter.size
            discardBefore must containTheSameElementsAs( deckAfter )
          }
        }

        "Both having an empty hand" in {
          afterSecondPlayerShuffles.players.flatMap(_.hand.cards) must beEmpty
        }
      }
    }

    "Continues by both players drawing cards" in {
      val afterBothPlayersDraw = {
        val firstPlayerDraws = DrawFromDeck.newHand
        afterSecondPlayerShuffles.nextToAct must be equalTo 0

        rules.moves(afterSecondPlayerShuffles) must be equalTo Set( firstPlayerDraws )

        val afterFirstPlayerDraws =
          rules.transition(afterSecondPlayerShuffles, firstPlayerDraws)

        val secondPlayerDraws = DrawFromDeck.newHand
        afterFirstPlayerDraws.nextToAct must be equalTo 1

        rules.moves(afterFirstPlayerDraws) must be equalTo Set( secondPlayerDraws )

        val afterSecondPlayerDraws =
          rules.transition(afterFirstPlayerDraws, firstPlayerDraws)

        afterSecondPlayerDraws
      }

      "From their deck" in {
        foreach( afterBothPlayersDraw.players ) {
          _.deck.cards must have size 5
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
