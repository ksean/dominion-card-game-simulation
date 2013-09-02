package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.card.{SupplyPile, Card}
import sa.ai.model.{Dominion, Game}

/**
 * Date: 01/09/13
 * Time: 1:13 AM
 */
class LastProvinceScenario extends SpecificationWithJUnit {
  "The game winning scenario" should {
    val lastProvinceState = Game.twoPlayerLastBuy

    "Have a player" in {
      val lastProvinceStatePlayer = lastProvinceState.players(lastProvinceState.nextToAct)

      "With a least one available buy" in {
        val lastProvinceStatePlayerBuys = Dominion.getBuys(lastProvinceStatePlayer)
        lastProvinceStatePlayerBuys must be greaterThanOrEqualTo 1

        "Where the player has treasure to spend" in {
          val lastProvinceStatePlayerTreasure = Dominion.getWealth(lastProvinceStatePlayer)

          "Greater than the cost of the province" in {
            val costOfLastProvince = Card.getCost(Card.Province)
            costOfLastProvince must be lessThanOrEqualTo lastProvinceStatePlayerTreasure

            "With one province card remaining" in {
              val inProvinceSupply = lastProvinceState.basic.province.cards.size
              inProvinceSupply must be equalTo 1
            }
          }
        }
      }
    }
  }
}
