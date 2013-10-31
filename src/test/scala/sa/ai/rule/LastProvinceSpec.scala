package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.{Dominion, BuyPhase, Game}
import sa.ai.model.card.{InPlay, Card}

/**
 * Date: 08/09/13
 * Time: 6:08 PM
 */
class LastProvinceSpec extends SpecificationWithJUnit {
  "The two-player game-winning province purchase" should {
    val winningState : Game =
      Game
        .empty
        .withProvincesRemaining(1)
        .withNextToAct(0)
        .withPlayer(0, Dominion.initialState)
        .withPhase(BuyPhase)
        .withPlayer(1, Dominion.initialState)
        .withInPlay(0, InPlay(Seq.fill(8)(Card.Copper)))


    "Have provinces remaining" in {
      val remainingProvinces =
        winningState.basic.province.cards.size

      "With only a single province" in {
        remainingProvinces must be equalTo 1
      }
    }

    "Have the next to act" in {
      val nextToAct = winningState.nextToAct

      "Player" in {
        val player = winningState.players(nextToAct)

        "Be in the buy phase" in {
          val playerPhase = winningState.phase
          playerPhase must be equalTo BuyPhase

          "With a usable buy" in {
            val playerBuys = player.buys
            playerBuys must be greaterThanOrEqualTo 1
          }
        }

        "With enough treasure" in {
          val playerTreasure = player.wealth

          "To afford a province card" in {
            val provinceCost = Card.Province.cost
            playerTreasure must be greaterThanOrEqualTo provinceCost
          }
        }

        "With enough resulting victory points" in {
          val playerVictoryPoints = player.victoryPoints + Card.Province.victory

          "To beat their opponent" in {
            val opponentVictoryPoints = winningState.players((nextToAct + 1) % 2).victoryPoints
            playerVictoryPoints must be greaterThan opponentVictoryPoints
          }
        }
      }
    }
  }
}
