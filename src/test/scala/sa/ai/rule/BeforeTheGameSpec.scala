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
              firstMove must be equalTo StartTheGameAction
            }
          }
        }
      }

      "Where the first player needs to start the game" in {
        val shuffle = StartTheGameAction

        "After which" in {
          val afterFirstPlayerShuffle = rules.transition(initialState, shuffle)

          "The first player's discard pile" in {
            val discard = afterFirstPlayerShuffle.dominions(0).discard

            "Is empty" in {
              discard.cards must beEmpty
            }
          }

          "The first player's deck" in {
            val deck = afterFirstPlayerShuffle.dominions(0).deck

            "Has 5 cards" in {
              deck.cards.size must be equalTo 5
            }
          }

          "The first player's hand" in {
            val hand = afterFirstPlayerShuffle.dominions(0).hand

            "Has 5 cards" in {
              hand.cards.size must be equalTo 5
            }
          }

          "The second player needs to shuffle" in {
            afterFirstPlayerShuffle.nextToAct must be equalTo 1
            rules.moves(afterFirstPlayerShuffle) must be equalTo Set(shuffle)
          }

          "The second player has not moved" in {
            afterFirstPlayerShuffle.dominions(1) must be equalTo initialState.dominions(1)
          }
        }
      }
    }


    val afterSecondPlayerShuffles : Game =
      rules.transition(
        initialState,
        List.fill(2)(StartTheGameAction)
      )
    "Start by both players shuffling and drawing 5 cards" in {
      "Where the second player shuffles after the first player" in {
        "Both having empty discard piles" in {
          afterSecondPlayerShuffles.dominions.flatMap(_.discard.cards) must beEmpty
        }

        "Both having 5 cards in their decks" in {
          afterSecondPlayerShuffles.dominions(0).deck.cards.size must be equalTo 5
          afterSecondPlayerShuffles.dominions(1).deck.cards.size must be equalTo 5
        }

        "Both having 5 cards in their hands" in {
          afterSecondPlayerShuffles.dominions(0).hand.cards.size must be equalTo 5
          afterSecondPlayerShuffles.dominions(1).hand.cards.size must be equalTo 5
        }
      }
    }

    /*"Continues by both players drawing cards" in {
      val afterBothPlayersDraw = {
          val firstPlayerDraws = StartTheGameAction
          afterSecondPlayerShuffles.nextToAct must be equalTo 0

        rules.moves(afterSecondPlayerShuffles) must be equalTo Set( firstPlayerDraws )

        val afterFirstPlayerDraws =
          rules.transition(afterSecondPlayerShuffles, firstPlayerDraws)

        val secondPlayerDraws = StartTheGameAction
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
    }*/
  }
}
