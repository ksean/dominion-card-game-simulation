package sa.ai.model

import org.specs2.mutable.SpecificationWithJUnit

/**
 * 10/07/13 9:28 PM
 */
class CardSpec extends SpecificationWithJUnit  {

  "Card model" should {
    // see: http://dominioncg.wikia.com/wiki/Smithy
    val smithyCard = Card("Smithy")

    "Have card named 'Smithy'" in {
      smithyCard.name must be equalTo "Smithy"
    }
  }

}

