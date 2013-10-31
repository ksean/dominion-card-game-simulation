package sa.ai.model.card

import Card._


/**
 * http://dominioncg.wikia.com/wiki/Kingdom_Cards
 */
case class Kingdom(supply : Seq[SupplyPile])

object Kingdom {
  val empty = Kingdom(Seq.empty)
  val firstGame = {
    val firstGameCards = Seq(
      Cellar, Market, Militia, Mine, Moat, Remodel, Smithy, Village, Woodcutter, Workshop)

    Kingdom(firstGameCards.map(SupplyPile(_, 10)))
  }
}
