package sa.ai.model

import sa.ai.model.card.{Kingdom, Basic, Card}

/**
 * Dominion game
 * http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf
 */
case class Game(
  players : Seq[Player],
  basic : Basic,
  kingdom : Kingdom
)

object Game {
  val twoPlayerInitialState = Game(
    Seq.fill(2)(Player.initialState),
    Basic.initialSetForTwoPlayers,
    Kingdom.firstGame
  )
}