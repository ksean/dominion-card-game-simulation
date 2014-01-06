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
    val scenario = Game.twoPlayerLastBuy

    "Have a player" in {
      val scenarioPlayer = scenario.dominions(scenario.nextToAct)

      "With at least one available buy" in {
        val availableBuys = scenarioPlayer.buys
        availableBuys must be greaterThanOrEqualTo 1

        "Where the player has treasure to spend" in {
          val treasureToSpend = scenarioPlayer.wealth

          "Greater than the cost of the province" in {
            val costOfLastProvince = Card.Province.cost
            costOfLastProvince must be lessThanOrEqualTo treasureToSpend

            "With one province card remaining" in {
              val provincesInSupply = scenario.basic.province.cards.size
              provincesInSupply must be equalTo 1
            }
          }
        }
      }
    }
  }
}
