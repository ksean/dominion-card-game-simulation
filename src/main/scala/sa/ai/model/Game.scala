package sa.ai.model

import sa.ai.model.card._
import sa.ai.rule._
import sa.ai.rule.OfficialRuleset
import scalaz.Digit._0
import sa.ai.player.InfoSet

/**
 * Dominion game
 */
case class Game(
  nextToAct : Int,
  dominions   : Seq[Dominion],
  basic     : Basic,
  kingdom   : Kingdom,
  phase     : Phase
) {
  def supply : Set[SupplyPile] =
    basic.supply ++ kingdom.supply

  def supply(card: Card) : Option[SupplyPile] =
    supply.find(_.card == card)



  def nextDominion : Dominion =
    dominions(nextToAct)

  def nextInfoSet : InfoSet =
    InfoSet(basic, nextDominion)


  def subtractSupply(card: Card) : Game  = {
    if(basic.supply.map(_.card).contains(card)) {
      val nextBasic : Basic =
        basic.subtractSupply(card)

      copy(basic = nextBasic)
    } else {
      ???
    }
    /*else if(kingdom.supply.map(_.card).contains(card)) {
      val nextKingdom : Kingdom =
        ???

      copy(kingdom = nextKingdom)
    }*/
  }


  def currentlyWinningPlayers() : Set[Int] = {

    val victoryPoints : Seq[Int] =
      (0 until dominions.size).map(victoryPoint)

    val maxPoints : Int =
      victoryPoints.max

    val oneWinner =
      victoryPoints.count(_ == maxPoints) == 1

    if (oneWinner)
    {
      Set(victoryPoints.indexOf(maxPoints))
    }
    else
    {
//            val winningCandidates : Set[Int] =
//              victoryPoints.index


      victoryPoints
        .zipWithIndex
        .filter((pointsIndex: (Int, Int)) => pointsIndex._1 == maxPoints)
        .map(_._2)
        .toSet
    }
  }

  def victoryPoint(playerIndex : Int) : Int =
    dominions(playerIndex).cards.map(_.victory).sum


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


  def withNextDominion(dominion: Dominion) : Game =
    withDominion(nextToAct, dominion)

  def withDominion(player: Int, dominion: Dominion) : Game = {
    val nextPlayers : Seq[Dominion] =
      dominions
        .padTo(player + 1, Dominion.initialState)
        .updated(player, dominion)

    copy(
      dominions = nextPlayers
    )
  }

  def withPhase(phase: Phase) : Game = {
    copy(
      phase = phase
    )
  }


  def withNextHand(handTransformer : (Hand => Hand)) : Game =
    withNextHand(handTransformer(nextDominion.hand))

  def withNextHand(nextHand : Hand) : Game =
    withHand(nextToAct, nextHand)

  def withHand(player: Int, hand: Hand) : Game = {
    val nextDominion : Dominion =
      dominions(player)
        .copy(hand = hand)

    withDominion(player, nextDominion)
  }



  def withNextInPlay(inPlayTransformer : (InPlay => InPlay)) : Game =
    withNextInPlay(inPlayTransformer(nextDominion.inPlay))

  def withNextInPlay(inPlayer: InPlay) : Game =
    withInPlay(nextToAct, inPlayer)

  def withInPlay(player: Int, inPlay: InPlay) : Game = {
    val nextDominion : Dominion =
      dominions(player)
        .copy(inPlay = inPlay)

    withDominion(player, nextDominion)
  }

  def withNextDiscard(discardTransformer : (DiscardPile => DiscardPile)) : Game =
    withDiscard(nextToAct, discardTransformer(nextDominion.discard))

  def withDiscard(player: Int, discard: DiscardPile) : Game = {
    val nextDominion : Dominion =
      dominions(player)
        .copy(discard = discard)

    withDominion(player, nextDominion)
  }

  def withNextSpent(spent : Int) : Game =
    withSpent(nextToAct, spent)

  def withSpent(player: Int, spent: Int) : Game = {
    val nextDominion : Dominion =
      dominions(player)
        .copy(spent = spent)

    withDominion(player, nextDominion)
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

  val twoPlayerRestrictedInitialState = Game(
    0,
    Seq.fill(2)(Dominion.initialState),
    Basic.initialRestrictedSetForTwoPlayers,
    Kingdom.empty,
    BeforeTheGamePhase
  )

  def twoPlayerFirstAction(rules : Ruleset) =
    rules.transition(
      Game.twoPlayerInitialState,
      List(
        StartTheGameAction,
        StartTheGameAction
      )).copy(phase = ActionPhase)

  def twoPlayerFirstBuy(rules : Ruleset) =
    rules.transition(
      Game.twoPlayerFirstAction(rules),
      NoAction
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