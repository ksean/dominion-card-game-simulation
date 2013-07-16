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

      "Include 3 basic treasure cards" in {
        "46 Coppers" in {
          basic.copper.cards.size must be equalTo 46
        }
        "40 Silvers" in {
          basic.silver.cards.size must be equalTo 40
        }
        "30 Golds" in {
          basic.gold.cards.size must be equalTo 30
        }
      }

      "Include 3 basic victory cards" in {
        "8 Estates" in {
          basic.estate.cards.size must be equalTo 8
        }
        "8 Duchies" in {
          basic.duchy.cards.size must be equalTo 8
        }
        "8 Provinces" in {
          basic.province.cards.size must be equalTo 8
        }
      }

      "Include 10 Curse cards" in {
        basic.curse.cards.size must be equalTo 10
      }

      "Includes an empty trash pile" in {
        basic.trash.cards must be empty
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
