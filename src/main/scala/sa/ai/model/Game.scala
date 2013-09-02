package sa.ai.model

import sa.ai.model.card._
import sa.ai.rule._
import sa.ai.rule.ShuffleDiscardIntoDeck
import sa.ai.rule.ShuffleDiscardIntoDeck
import sa.ai.rule.PutSetAsideIntoDiscard
import sa.ai.rule.PutHandIntoDiscard

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

/*
 * TODO: getState() should return the state of the game given certain parameters that are yet to be defined
 */
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

  val twoPlayerFirstCleanup =
    Ruleset.transition(
      Game.twoPlayerFirstBuy,
      List(
        PutHandIntoDiscard(),
        PutSetAsideIntoDiscard(),
        DrawFromDeck(5)
    )).copy(phase = CleanupPhase)

  val twoPlayerLastBuy = Game(
    0,
    Seq(Dominion.initialState.copy(Dominion.initialState.discard,Dominion.initialState.deck,Dominion.initialState.hand,InPlay(Seq(Card.Gold, Card.Gold, Card.Gold))), Dominion.initialState),
    Basic.initialSetForTwoPlayers.copy(province = Pile(Card.Province, 1)),
    Kingdom.firstGame,
    BuyPhase
  )
}