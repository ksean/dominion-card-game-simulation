package sa.ai.rule

import sa.ai.model._
import sa.ai.model.card._
import scala.annotation.tailrec

/**
 * http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf
 */
object Ruleset
{
  def moves(state:Game) : Set[Move] =
    {
      val currentPlayers = state.players
      val nextToAct = state.nextToAct
      val nextPlayerHand = currentPlayers(nextToAct).hand.cards
      val nextPlayerDeck = currentPlayers(nextToAct).deck.cards

      if (state.phase == ActionPhase || state.phase == BeforeTheGamePhase)
      {
        if (nextPlayerHand.isEmpty)
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
        else if (!nextPlayerHand.map(_.cardType).contains(ActionType))
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

    val currentPlayerWealth : Int =
      state.nextPlayer.wealth

    val affordableSupplyPiles : Set[SupplyPile] =
      availableSupplyPiles.filter(
        _.card.cost <= currentPlayerWealth)

    val affordableCardToSupply : Map[Card, SupplyPile] =
      affordableSupplyPiles
        .groupBy(_.card)
        .transform((cardType: Card, piles: Set[SupplyPile]) => {
        assert(piles.size == 1)
        piles.iterator.next()
      })

    val provinceSupply : Option[SupplyPile] =
      affordableCardToSupply.get(Card.Province)

    val provincePurchase : Option[Move] =
      provinceSupply.map((pile: SupplyPile) =>
        Buy(pile.card))

    Set(NoBuy) ++ provincePurchase
  }


  @tailrec
  final def transition(state:Game, moves:Seq[Move])(implicit shuffler : Shuffler) : Game =
    moves match {
      case Nil => state
      case next :: rest => transition(transition(state, next)(shuffler), rest)
    }

  def transition(state:Game, move:Move)(implicit shuffler : Shuffler) : Game = {
    move match {
      case ShuffleDiscardIntoDeck => {
        val playerIndex = state.nextToAct
        val currentPlayers = state.players
        val transitioningPlayer = currentPlayers(playerIndex)
        val nextDiscardPile = DiscardPile(Seq())
        val nextDeck = shuffler.shuffle(transitioningPlayer.discard)
        val nextPlayerState = Dominion(nextDiscardPile, nextDeck, transitioningPlayer.hand)
        val nextPlayers = currentPlayers.updated(playerIndex, nextPlayerState)
        val nextNextToAct = (state.nextToAct + 1) % 2

        state.copy(players = nextPlayers, nextToAct = nextNextToAct)
      }

      case DrawFromDeck(count) => {
        val playerIndex = state.nextToAct
        val currentPlayers = state.players
        val transitioningPlayer = currentPlayers(playerIndex)
        val (drawn, remaining) = transitioningPlayer.deck.cards.splitAt(count)
        val nextHand = Hand(transitioningPlayer.hand.cards ++ drawn)
        val nextDeck = Deck(remaining)
        val nextPlayerState = Dominion(transitioningPlayer.discard, nextDeck, nextHand)
        val nextPlayers = currentPlayers.updated(playerIndex, nextPlayerState)
        val nextNextToAct = (state.nextToAct + 1) % 2
        val nextPhase = {
          playerIndex match {
            case 0 => state.phase
            case 1 => ActionPhase
          }
        }

        state.copy(players = nextPlayers, nextToAct = nextNextToAct, phase = nextPhase)
      }

      case NoAction => {
        val treasureInHand : Seq[Card] =
          state.nextPlayer.hand.cards.filter(_.cardType == Treasure)

        state
          .withPhase(BuyPhase)
          .withNextInPlay(_.add(treasureInHand))
          .withNextHand(_.remove(treasureInHand))
      }

      case NoBuy => {
        state
        .withPhase(CleanupPhase)
      }

      case PutHandIntoDiscard => {
        val currentPlayers = state.players
        val transitioningPlayer = state.nextPlayer
        val transitioningPlayersDiscard = transitioningPlayer.discard
        val transitioningPlayersHand = transitioningPlayer.hand
        val nextDiscard = DiscardPile(transitioningPlayersDiscard.cards ++ transitioningPlayersHand.cards)
        val nextHand = Hand(Seq())
        val nextDeck = transitioningPlayer.deck
        val nextPlayerState = Dominion(nextDiscard,nextDeck,nextHand)
        val nextPlayers = currentPlayers.updated(state.nextToAct, nextPlayerState)
        state.copy(players = nextPlayers)
      }

      case PutSetAsideIntoDiscard => {
        state
      }

      case Buy(card) => {
        val nextPlayer = state.nextPlayer
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
            .withPlayer(state.nextToAct, nextNextPlayer)
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

      case CleanupAction => {
        val beforeDraw : Game =
          state
            .withNextDiscard(_.add(state.nextPlayer.inPlay))
            .withNextDiscard(_.add(state.nextPlayer.hand))
            .withNextHand(Hand.empty)
            .withNextSpent(0)
            .withNextInPlay(InPlay.empty)

        transition(beforeDraw, DrawFromDeck.newHand)(shuffler)
      }
    }
  }
}
