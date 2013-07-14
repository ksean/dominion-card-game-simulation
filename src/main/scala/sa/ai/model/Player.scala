package sa.ai.model

import sa.ai.model.card.Card

/**
 * Dominion player
 */
case class Player(
  id : Int,
  deck : List[Card],
  discard : List[Card],
  hand : List[Card]
)