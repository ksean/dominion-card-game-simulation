package sa.ai.model

import sa.ai.model.card.Card

/**
 * Dominion turn
 */
case class Turn(
  owner : Dominion,
  cards : List[Card]
)