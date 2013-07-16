package sa.ai.model

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.card.Card

/**
 * Dominion card specification
 */
class CardSpec extends SpecificationWithJUnit
{
  "Dominion Card model" should {

    "With a 'Smithy'" in {
      Card.Smithy.name must be equalTo "Smithy"
      // see: http://dominioncg.wikia.com/wiki/Smithy
    }
  }

}

