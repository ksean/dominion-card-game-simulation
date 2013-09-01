package sa.ai.model

import sa.ai.model.card.{Kingdom, Basic, Card}
import sa.ai.rule._
import sa.ai.rule.ShuffleDiscardIntoDeck

/**
 * Dominion game
 */
case class Game(
  nextToAct : Int,
  players : Seq[Dominion],
  basic : Basic,
  kingdom : Kingdom,
  phase : Phase
)

object Game {
  val twoPlayerInitialState = Game(
    0,
    Seq.fill(2)(Dominion.initialState),
    Basic.initialSetForTwoPlayers,
    Kingdom.firstGame,
    BeforeTheGame
  )

  val twoPlayerFirstAction =
    Ruleset.transition(
      Game.twoPlayerInitialState,
      List(
        ShuffleDiscardIntoDeck(),
        ShuffleDiscardIntoDeck(),
        DrawFromDeck.initialHand,
        DrawFromDeck.initialHand
      ))

  val twoPlayerFirstBuy =
    Ruleset.transition(
      Game.twoPlayerFirstAction,
      NoBuy
    ).copy(phase = BuyPhase)
}