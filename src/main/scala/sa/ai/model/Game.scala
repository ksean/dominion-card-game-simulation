package sa.ai.model

import sa.ai.model.card.{Kingdom, Basic, Card}
import sa.ai.rule.{DrawFromDeck, ShuffleDiscardIntoDeck, Ruleset}

/**
 * Dominion game
 */
case class Game(
  nextToAct : Int,
  players : Seq[Dominion],
  basic : Basic,
  kingdom : Kingdom
  //phase : Phase
)

object Game {
  val twoPlayerInitialState = Game(
    0,
    Seq.fill(2)(Dominion.initialState),
    Basic.initialSetForTwoPlayers,
    Kingdom.firstGame
    //Phase.BeforeTheGame
  )

  val twoPlayerFirstAction =
    Ruleset.transition(
      Game.twoPlayerInitialState,
      List(
        ShuffleDiscardIntoDeck(),
        ShuffleDiscardIntoDeck(),
        DrawFromDeck.initialHand,
        DrawFromDeck.initialHand
      )
    )
}