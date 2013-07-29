package sa.ai.model

import sa.ai.model.card.{Hand, Deck, DiscardPile, Card}

/**
 * Player's dominion
 */
case class Dominion(
  discard : DiscardPile,
  deck : Deck,
  hand : Hand = Hand.empty
)

object Dominion {
  val initialState = {
    val coppers = Seq.fill(7)(Card.Copper)
    val estates = Seq.fill(3)(Card.Estate)

    val deck = Deck(Seq())

    Dominion(DiscardPile(coppers ++ estates), deck)
  }
}