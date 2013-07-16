package sa.ai.model

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.card.{Card, SupplyPile}

/**
 * Card pile specification
 */
class PileSpec extends SpecificationWithJUnit
{
  "Supply pile" should {
    "Have zero cards when empty" in {
      SupplyPile(Card.Curse, 0).cards must be empty
    }

    "Have some number of cards" in {
      val cardCount = 3
      val uniformPile = SupplyPile(Card.Curse, cardCount)

      "that are uniform" in {
        uniformPile.cards.toSet.size must be equalTo 1
      }
    }
  }
}
