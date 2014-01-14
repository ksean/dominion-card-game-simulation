package sa.ai.rule

import sa.ai.model._
import sa.ai.model.card._


case class OfficialRuleset(
    shuffler : Shuffler
  ) extends Ruleset
{
  //--------------------------------------------------------------------------------------------------------------------
  override def moves(state:Game) : Set[Move] =
  {
    val currentPlayers = state.dominions
    val nextToAct = state.nextToAct
    val nextPlayerHand = currentPlayers(nextToAct).hand.cards
//    val nextPlayerDeck = currentPlayers(nextToAct).deck.cards
    if (state.phase == BeforeTheGamePhase)
    {
      Set(StartTheGameAction)
    }
    else if (state.phase == ActionPhase)
    {
      /*if (nextPlayerHand.isEmpty)
      {
        if (nextPlayerDeck.isEmpty)
        {
          Set(ShuffleDiscardIntoDeck)
        }
        else
        {
          Set(DrawFromDeck(5))
        }
      }
      else*/
      if (!nextPlayerHand.map(_.cardType).contains(ActionType))
      {
        Set(NoAction)
      }
      else
      {
        Set(NoAction)
      }
    }
    else if (state.phase == BuyPhase)
    {
      buyPhase(state)
    }
    else if (state.phase == AfterTheGamePhase)
    {
      Set(PutHandIntoDiscard,PutSetAsideIntoDiscard,DrawFromDeck(5))
    }
    else if (state.phase == CleanupPhase)
    {
      Set(CleanupAction)
    }
    else
    {
      Set(NoBuy)
    }
  }

  def buyPhase(state:Game) : Set[Move] =
  {
    val availableSupplyPiles : Set[SupplyPile] =
      state.supply.filter(_.size > 0)

    val nextPlayer : Dominion =
      state.nextDominion

    val currentPlayerWealth : Int =
      nextPlayer.wealth

    val hasBuys : Boolean =
      nextPlayer.buys > 0

    if (! hasBuys) {
      return Set(NoBuy)
    }

    val currentPlayerAvailableWealth : Int =
      currentPlayerWealth - nextPlayer.spent

    val affordableSupplyPiles : Set[SupplyPile] =
      availableSupplyPiles.filter(
        _.card.cost <= currentPlayerAvailableWealth)

    val affordableCardToSupply : Map[Card, SupplyPile] =
      affordableSupplyPiles
        .groupBy(_.card)
        .transform((cardType: Card, piles: Set[SupplyPile]) => {
        assert(piles.size >= 1)
        piles.iterator.next()
      })

    val affordableCardsInSupply : Set[Card] =
      affordableCardToSupply.keySet

    val affordableBuys : Set[Move] =
      affordableCardsInSupply.map(Buy)

    Set(NoBuy) ++ affordableBuys
  }



  //--------------------------------------------------------------------------------------------------------------------
  override def transition(state : Game, move : Move) : Game = {
    {
      val legalMoves = moves(state)
      assert(legalMoves.contains(move), s"Illegal move: $move, must be one of: $legalMoves")
    }

    move match {
      case StartTheGameAction =>
        startTheGameAction(state)

      case ShuffleDiscardIntoDeck =>
        shuffleDiscardUnderDeck(state)

      case DrawFromDeck(count) =>
        drawFromDeck(state, count)

      case NoAction =>
        noAction(state)

      case NoBuy =>
        state
          .withPhase(CleanupPhase)

      case PutHandIntoDiscard =>
        putHandIntoDiscard(state)

      case PutSetAsideIntoDiscard =>
        state

      case Buy(card) =>
        buy(state, card)

      case CleanupAction =>
        cleanupAction(state)
    }
  }


  def shuffleDiscardUnderDeck(state : Game) : Game = {
    val playerIndex = state.nextToAct
    val currentPlayers = state.dominions
    val transitioningPlayer = currentPlayers(playerIndex)
    val nextDiscardPile = DiscardPile(Seq())

    // transitioningPlayer.deck.cards
    val addedToDeck : Seq[Card] =
      shuffler.shuffle(transitioningPlayer.discard).cards

    val nextDeck = Deck(transitioningPlayer.deck.cards ++ addedToDeck)

    val nextPlayerState = Dominion(nextDiscardPile, nextDeck, transitioningPlayer.hand)
    val nextPlayers = currentPlayers.updated(playerIndex, nextPlayerState)
//    val nextNextToAct = (state.nextToAct + 1) % 2

//    state.copy(players = nextPlayers, nextToAct = nextNextToAct)
    state.copy(dominions = nextPlayers)
  }

  def startTheGameAction(state: Game) : Game = {
    val afterShuffle = shuffleDiscardUnderDeck(state)
    val afterDraw = drawFromDeck(afterShuffle, 5)

    val nextPlayerIndex : Int =
      advancePlayer(state)

    val withNextPlayerIndex : Game =
      afterDraw.withNextToAct(nextPlayerIndex)

    if (nextPlayerIndex == 0) {
      withNextPlayerIndex.withPhase(ActionPhase)
    } else {
      withNextPlayerIndex
    }
  }

  private def advancePlayer(state: Game) : Int =
    (state.nextToAct + 1) % state.dominions.size


  def drawFromDeck(state:Game, count : Int) : Game = {
    val playerIndex = state.nextToAct
    val currentPlayers = state.dominions
    val transitioningPlayer = currentPlayers(playerIndex)

    val cardsBeforeShuffle = transitioningPlayer.deck.cards.size
    val cardsIfNeedShuffle = if (cardsBeforeShuffle == 0) {
      shuffleDiscardUnderDeck(state)
    } else if(cardsBeforeShuffle < count) {
      shuffleDiscardUnderDeck(state)
    } else state

    val afterShufflePlayers = cardsIfNeedShuffle.dominions
    val afterShuffleTransitioningPlayer = afterShufflePlayers(playerIndex)
    val (drawn, remaining) = afterShuffleTransitioningPlayer.deck.cards.splitAt(count)

//    println(s"drawing from deck: $playerIndex | $count | ${drawn.map(_.name)} | ${remaining.map(_.name)}")

    val nextHand = Hand(afterShuffleTransitioningPlayer.hand.cards ++ drawn)
    val nextDeck = Deck(remaining)
    val nextPlayerState = Dominion(afterShuffleTransitioningPlayer.discard, nextDeck, nextHand)
    val nextPlayers = afterShufflePlayers.updated(playerIndex, nextPlayerState)
//    val nextNextToAct = (state.nextToAct + 1) % 2
    /*val nextPhase = {
      playerIndex match {
        case 0 => state.phase
        case 1 => ActionPhase
      }
    }*/

//    state.copy(players = nextPlayers, nextToAct = nextNextToAct, phase = nextPhase)
    state.copy(dominions = nextPlayers)
  }

  def noAction(state : Game) : Game = {
    val treasureInHand : Seq[Card] =
      state.nextDominion.hand.cards.filter(_.cardType == Treasure)

    state
      .withPhase(BuyPhase)
      .withNextInPlay(_.add(treasureInHand))
      .withNextHand(_.remove(treasureInHand))
  }

  def putHandIntoDiscard(state : Game) : Game = {
    val currentPlayers = state.dominions
    val transitioningPlayer = state.nextDominion
    val transitioningPlayersDiscard = transitioningPlayer.discard
    val transitioningPlayersHand = transitioningPlayer.hand
    val nextDiscard = DiscardPile(transitioningPlayersDiscard.cards ++ transitioningPlayersHand.cards)
    val nextHand = Hand(Seq())
    val nextDeck = transitioningPlayer.deck
    val nextPlayerState = Dominion(nextDiscard,nextDeck,nextHand)
    val nextPlayers = currentPlayers.updated(state.nextToAct, nextPlayerState)
    state.copy(dominions = nextPlayers)
  }

  def buy(state : Game, card : Card) : Game = {
    val nextPlayer = state.nextDominion
    assert(nextPlayer.buys             >= 1        , "Must have at least one buy"               )
    assert(nextPlayer.availableWealth  >= card.cost, "Must be able to afford the card"          )
    assert(state.supply(card).get.size >= 1        , "Must have at least one card in the supply")
    assert(state.phase                 == BuyPhase , "Must be in the buy phase"                 )

    val nextNextPlayer : Dominion =
      nextPlayer
        .withBuys(nextPlayer.buys - 1)
        .withSpent(nextPlayer.spent + card.cost)
        .addDiscard(card)

    val preliminaryNextState : Game =
      state
        .withDominion(state.nextToAct, nextNextPlayer)
        .subtractSupply(card)

    //        val nextSupply : SupplyPile =
    //          state.supply(card).get.plusSize(-1)
    //
    //
    //          state.withPlayer(state.nextToAct, nextNextPlayer)
    //          state.subtractSupply(card)

    val nextState : Game =
      if (card == Card.Province && preliminaryNextState.basic.province.size == 0) {
        preliminaryNextState.withPhase(AfterTheGamePhase)
      } else {
        preliminaryNextState
      }

    nextState
  }

  def cleanupAction(state : Game) : Game = {
    val beforeDraw : Game =
      state
        .withNextDiscard(_.add(state.nextDominion.hand.cards ++ state.nextDominion.inPlay.cards))
        .withNextHand(Hand.empty)
        .withNextSpent(0)
        .withNextInPlay(InPlay.empty)

    val afterDraw : Game =
      drawFromDeck(beforeDraw, 5)
        .withNextToAct(advancePlayer(state))
        .withPhase(ActionPhase)

    afterDraw
  }

}
