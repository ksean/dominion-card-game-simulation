package sa.ai.model

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