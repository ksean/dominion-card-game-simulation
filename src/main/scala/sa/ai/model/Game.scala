package sa.ai.model

import sa.ai.model.card.Card

/**
 * Dominion game
 */
case class Game(
  id : Double,
  players : List[Player],
  cards : List[Card],
  turns : List[Turn],
  trash : List[Card]
)