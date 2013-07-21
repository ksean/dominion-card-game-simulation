package sa.ai.rule

/**
 * 15/07/13 9:26 PM
 */
sealed class Move(val playerIndex : Int)

case class ShuffleDiscardIntoDeck(override val playerIndex : Int) extends Move(playerIndex)
case class DrawFromDeck(override val playerIndex : Int, cardCount : Int) extends Move(playerIndex)

