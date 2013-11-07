package sa.ai.model.card

import Card._


/**
 * http://dominioncg.wikia.com/wiki/Kingdom_Cards
 */
case class Kingdom(supply : Set[SupplyPile])

object Kingdom {
  val empty = Kingdom(Set.empty)
  val firstGame = {
    val firstGameCards = Set(
      Cellar, Market, Militia, Mine, Moat, Remodel, Smithy, Village, Woodcutter, Workshop)

    Kingdom(firstGameCards.map(SupplyPile(_, 10)))
  }
}
