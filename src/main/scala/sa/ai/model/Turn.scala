package sa.ai.model

import sa.ai.model.card.Card

/**
 * Dominion turn
 */
case class Turn(
  owner : Player,
  cards : List[Card]
)