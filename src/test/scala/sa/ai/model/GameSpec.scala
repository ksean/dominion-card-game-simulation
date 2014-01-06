package sa.ai.model

import org.specs2.mutable.SpecificationWithJUnit

/**
 * 20/07/13 8:53 PM
 */
class GameSpec extends SpecificationWithJUnit
{
  "Dominion card set" should {

    "Have a pre-defined initial state" in {
      val initial = Game.twoPlayerInitialState

      val players = initial.dominions
      "For two players" in {
        "With a player count of two" in {
          players.size must be equalTo 2
        }

        "Where each player has a discard pile"  in {
          val discardPiles = players.map(_.discard)

          "Containing 10 cards" in {
            foreach (discardPiles) {
              _.cards must have size 10
            }
          }
        }
      }

      "Where the discard piles of each player have 10 cards"  in {
        foreach (players) {
          _.discard.cards must have size 10
        }
      }
    }
  }
}

