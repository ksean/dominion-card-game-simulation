package sa.ai.rule

import sa.ai.model.{Player, Game}
import sa.ai.model.card.{Hand, DiscardPile, Deck}
import scala.annotation.tailrec

/**
 * http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf
 */
object Ruleset
{
  def actions(state:Game) : Set[Move] = Set(ShuffleDiscardIntoDeck(0))

  @tailrec
  def transition(state:Game, moves:List[Move]) : Game =
    moves match {
      case Nil => state
      case next :: rest => transition(transition(state, next), rest)
    }

  def transition(state:Game, move:Move) : Game = {
    move match {
      case ShuffleDiscardIntoDeck(playerIndex) => {
        val currentPlayers = state.players
        val transitioningPlayer = currentPlayers(playerIndex)
        val nextDiscardPile = DiscardPile(Seq())
        val nextDeck = Deck(transitioningPlayer.discard.cards)
        val nextPlayerState = Player(nextDiscardPile, nextDeck, transitioningPlayer.hand)
        val nextPlayers = currentPlayers.updated(playerIndex, nextPlayerState)

        state.copy(players = nextPlayers)
      }

      case DrawFromDeck(playerIndex, count) => {
        val currentPlayers = state.players
        val transitioningPlayer = currentPlayers(playerIndex)
        val (drawn, remaining) = transitioningPlayer.deck.cards.splitAt(count)
        val nextHand = Hand(transitioningPlayer.hand.cards ++ drawn)
        val nextDeck = Deck(remaining)
        val nextPlayerState = Player(transitioningPlayer.discard, nextDeck, nextHand)
        val nextPlayers = currentPlayers.updated(playerIndex, nextPlayerState)


        state.copy(players = nextPlayers)
      }
    }
  }
}
