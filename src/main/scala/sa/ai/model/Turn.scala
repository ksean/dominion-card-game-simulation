package sa.ai.model

import sa.ai.model.card.Card

/**
 * Dominion turn
 */
case class Turn(
  id : Int,
  owner : Player,
  cards : List[Card]
)