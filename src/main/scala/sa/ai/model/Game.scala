package sa.ai.model

import sa.ai.model.card.{Kingdom, Basic, Card}

/**
 * Dominion game
 * http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf
 */
case class Game(
//  players : List[Player],
  basic : Basic,
  kingdom : Kingdom
)