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
  players   : Seq[Dominion],
  basic     : Basic,
  kingdom   : Kingdom,
  phase     : Phase
) {
  def supply : Set[SupplyPile] =
    basic.supply ++ kingdom.supply

  def supply(card: Card) : Option[SupplyPile] =
    supply.find(_.card == card)



  def nextPlayer : Dominion =
    players(nextToAct)


  def subtractSupply(card: Card) : Game  = {
    if(basic.supply.map(_.card).contains(card)) {
      val nextBasic : Basic =
        basic.subtractCard(card)

      copy(basic = nextBasic)
    }
    else {
      ???
    }
    
  }
    
    


  def withProvincesRemaining(count: Int) : Game = {
    copy(
      basic = basic.copy(
        province = SupplyPile(Card.Province, count)))
  }

  def withNextToAct(player: Int) : Game = {
    copy(
      nextToAct = player
    )
  }


  def withNextPlayer(dominion: Dominion) : Game =
    withPlayer(nextToAct, dominion)

  def withPlayer(player: Int, dominion: Dominion) : Game = {
    val nextPlayers : Seq[Dominion] =
      players
        .padTo(player + 1, Dominion.initialState)
        .updated(player, dominion)

    copy(
      players = nextPlayers
    )
  }

  def withPhase(phase: Phase) : Game = {
    copy(
      phase = phase
    )
  }


  def withNextHand(handTransformer : (Hand => Hand)) : Game =
    withNextHand(handTransformer(nextPlayer.hand))

  def withNextHand(nextHand : Hand) : Game =
    withHand(nextToAct, nextHand)

  def withHand(player: Int, hand: Hand) : Game = {
    val nextDominion : Dominion =
      players(player)
        .copy(hand = hand)

    withPlayer(player, nextDominion)
  }



  def withNextInPlay(inPlayTransformer : (InPlay => InPlay)) : Game =
    withNextInPlay(inPlayTransformer(nextPlayer.inPlay))

  def withNextInPlay(inPlayer: InPlay) : Game =
    withInPlay(nextToAct, inPlayer)

  def withInPlay(player: Int, inPlay: InPlay) : Game = {
    val nextDominion : Dominion =
      players(player)
        .copy(inPlay = inPlay)

    withPlayer(player, nextDominion)
  }

  def withNextDiscard(discardTransformer : (DiscardPile => DiscardPile)) : Game =
    withDiscard(nextToAct, discardTransformer(nextPlayer.discard))

  def withDiscard(player: Int, discard: DiscardPile) : Game = {
    val nextDominion : Dominion =
      players(player)
        .copy(discard = discard)

    withPlayer(player, nextDominion)
  }

  def withNextSpent(spent : Int) : Game =
    withSpent(nextToAct, spent)

  def withSpent(player: Int, spent: Int) : Game = {
    val nextDominion : Dominion =
      players(player)
        .copy(spent = spent)

    withPlayer(player, nextDominion)
  }
}

object Game {
  val empty = Game(
    -1,
    Seq.empty,
    Basic.empty,
    Kingdom.empty,
    BeforeTheGamePhase
  )

  val twoPlayerInitialState = Game(
    0,
    Seq.fill(2)(Dominion.initialState),
    Basic.initialSetForTwoPlayers,
    Kingdom.firstGame,
    BeforeTheGamePhase
  )

  def twoPlayerFirstAction(shuffler : Shuffler = Shuffler.passThrough) =
    OfficialRuleset(shuffler).transition(
      Game.twoPlayerInitialState,
      List(
        ShuffleDiscardIntoDeck,
        ShuffleDiscardIntoDeck,
        DrawFromDeck.newHand,
        DrawFromDeck.newHand
      ))

  def twoPlayerFirstBuy(shuffler : Shuffler = Shuffler.passThrough) =
    OfficialRuleset(shuffler).transition(
      Game.twoPlayerFirstAction(shuffler),
      NoBuy
    ).copy(phase = BuyPhase)

  val twoPlayerLastBuy = Game(
    0,
    Seq(
      Dominion.initialState.copy(
        Dominion.initialState.discard,
        Dominion.initialState.deck,
        Dominion.initialState.hand,
        InPlay(Seq(Card.Gold, Card.Gold, Card.Gold))
      ),
      Dominion.initialState),
    Basic.initialSetForTwoPlayers.copy(province = Pile(Card.Province, 1)),
    Kingdom.firstGame,
    BuyPhase
  )
}