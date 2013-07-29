package sa.ai.model

import sa.ai.model.card.{Kingdom, Basic, Card}

/**
 * Dominion game
 */
case class Game(
  players : Seq[Dominion],
  basic : Basic,
  kingdom : Kingdom
)

object Game {
  val twoPlayerInitialState = Game(
    Seq.fill(2)(Dominion.initialState),
    Basic.initialSetForTwoPlayers,
    Kingdom.firstGame
  )
}