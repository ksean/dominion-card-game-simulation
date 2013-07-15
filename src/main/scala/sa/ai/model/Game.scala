package sa.ai.model

import sa.ai.model.card.Card

/**
 * Dominion game
 * http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf
 */
case class Game(
  id : Double,
  players : List[Player],
  cards : List[Card],
  turns : List[Turn],
  trash : List[Card]
)