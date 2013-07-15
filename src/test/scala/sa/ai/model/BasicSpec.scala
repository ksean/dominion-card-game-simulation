package sa.ai.model

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.card.{Basic, Card}

/**
 * 13/07/13 9:06 PM
 */
class BasicSpec extends SpecificationWithJUnit
{
  "Basic Cards" should {

    "At the beginning of a two player game" in {
      val basic = Basic.initialSetForTwoPlayers

      "Include three basic treasures" in {

        "46 coppers" in {
          basic.copper.cards.size must be equalTo 46
        }

        "40 silvers" in {
          basic.silver.cards.size must be equalTo 40
        }

        "30 golds" in {
          basic.gold.cards.size must be equalTo 30
        }
      }
    }
  }

//  "Dominion Card model" should {
//
//    "Have a 'First Game' card set" in {
//      val firstGameCards = Card.FirstGame
//
//      "With 10 cards" in {
//        firstGameCards.size must be equalTo 10
//      }
//
//      "With a 'Smithy'" in {
//        firstGameCards.map(_.name) must contain("Smithy")
//        // see: http://dominioncg.wikia.com/wiki/Smithy
//      }
//    }
//  }
}
