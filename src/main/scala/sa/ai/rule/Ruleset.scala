package sa.ai.rule

import sa.ai.model._
import sa.ai.model.card.{ActionType, Hand, DiscardPile, Deck}
import scala.annotation.tailrec
import sa.ai.model.card.Deck
import sa.ai.model.card.DiscardPile

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

      if (state.phase == ActionPhase || state.phase == BeforeTheGame)
      {
        if (nextPlayerHand.isEmpty)
        {
          if (nextPlayerDeck.isEmpty)
          {
            Set(ShuffleDiscardIntoDeck())
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
        Set(NoBuy)
      }
      else if (state.phase == CleanupPhase)
      {
        Set(PutHandIntoDiscard(),PutSetAsideIntoDiscard(),DrawFromDeck(5))
      }
      else
      {
        Set(NoBuy)
      }
    }

  @tailrec
  final def transition(state:Game, moves:List[Move]) : Game =
    moves match {
      case Nil => state
      case next :: rest => transition(transition(state, next), rest)
    }

  def transition(state:Game, move:Move)(implicit shuffler : Shuffler) : Game = {
    move match {
      case ShuffleDiscardIntoDeck() => {
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
        state
      }

      case NoBuy => {
        state
      }

      case PutHandIntoDiscard() => {
        val playerIndex = state.nextToAct
        val currentPlayers = state.players
        val transitioningPlayer = currentPlayers(playerIndex)
        val transitioningPlayersDiscard = transitioningPlayer.discard
        val transitioningPlayersHand = transitioningPlayer.hand
        val nextDiscard = DiscardPile(transitioningPlayersDiscard.cards ++ transitioningPlayersHand.cards)
        val nextHand = Hand(Seq())
        val nextDeck = transitioningPlayer.deck
        val nextPlayerState = Dominion(nextDiscard,nextDeck,nextHand)
        val nextPlayers = currentPlayers.updated(playerIndex, nextPlayerState)
        state.copy(players = nextPlayers)
      }

      case PutSetAsideIntoDiscard() => {
        state
      }
    }
  }
}
