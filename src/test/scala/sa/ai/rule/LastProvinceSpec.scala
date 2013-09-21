package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.{BuyPhase, Game}
import sa.ai.model.card.Card

/**
 * Date: 08/09/13
 * Time: 6:08 PM
 */
class LastProvinceSpec extends SpecificationWithJUnit {
  "The two-player game-winning province purchase" should {
    val winningState = Game(???)

    "Have one province card remaining" in {
      val remainingProvinces = winningState.basic.province.cards.size
      remainingProvinces must be equalTo 1
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
