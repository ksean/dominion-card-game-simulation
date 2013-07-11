package sa.ai.model

import org.specs2.mutable.SpecificationWithJUnit

/**
 * Dominion card specification
 */
class CardSpec extends SpecificationWithJUnit
{
  "Dominion Card model" should {

    "Have a 'First Game' card set" in {
      val firstGameCards = Card.FirstGame

      "With 10 cards" in {
        firstGameCards.size must be equalTo 10
      }

      "With a 'Smithy'" in {
        firstGameCards.map(_.name) must contain("Smithy")
        // see: http://dominioncg.wikia.com/wiki/Smithy
      }
    }
  }

}

