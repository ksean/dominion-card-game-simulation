package sa.ai.model

import sa.ai.model.card.{DiscardPile, Card}

/**
 * Dominion player
 */
case class Player(
  discard : DiscardPile
//  deck : List[Card],
//  discard : List[Card],
//  hand : List[Card]
)

object Player {
  val initialState = {
    val coppers = List.fill(7)(Card.Copper)
    val estates = List.fill(3)(Card.Estate)

    Player(DiscardPile(coppers ++ estates))
  }
}