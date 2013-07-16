package sa.ai.model

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.card.Kingdom

/**
 * 15/07/13 8:25 PM
 */
class KingdomSpec extends SpecificationWithJUnit
{
  "Kingdom cards" should {

    "Be predefined for first game" in {
      val kingdom = Kingdom.firstGame

      "With 10 unique supply piles" in {
        kingdom.supply.toSet.size must be equalTo 10
      }
    }
  }
}
