package sa.ai.model

import sa.ai.model.card.{Hand, Deck, DiscardPile, Card}

/**
 * Dominion player
 */
case class Player(
  discard : DiscardPile,
  deck : Deck,
  hand : Hand = Hand.empty
)

object Player {
  val initialState = {
    val coppers = Seq.fill(7)(Card.Copper)
    val estates = Seq.fill(3)(Card.Estate)

    val deck = Deck(Seq())

    Player(DiscardPile(coppers ++ estates), deck)
  }
}