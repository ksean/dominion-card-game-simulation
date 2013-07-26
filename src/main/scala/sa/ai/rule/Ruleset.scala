package sa.ai.rule

import sa.ai.model.{Player, Game}
import sa.ai.model.card.{DiscardPile, Deck}

/**
 * http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf
 */
object Ruleset
{
  def actions(state:Game) : Set[Move] = Set(ShuffleDiscardIntoDeck(0))

  def transition(state:Game, move:Move) : Game = {
    move match {
      case ShuffleDiscardIntoDeck(playerIndex) => {
        val currentPlayers = state.players
        val transitioningPlayer = currentPlayers(playerIndex)
        val nextDiscardPile = DiscardPile(Seq())
        val nextDeck = Deck(transitioningPlayer.discard.cards)
        val nextPlayerState = Player(nextDiscardPile, nextDeck)
        val nextPlayers = currentPlayers.updated(playerIndex, nextPlayerState)

        state.copy(players = nextPlayers)
      }

      case DrawFromDeck(playerIndex, count) => {
        null
      }
    }
  }
}
