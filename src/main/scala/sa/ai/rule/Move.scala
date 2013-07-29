package sa.ai.rule

/**
 * 15/07/13 9:26 PM
 */
sealed abstract class Move() {
  def playerIndex : Int
}

case class ShuffleDiscardIntoDeck(playerIndex : Int) extends Move


case class DrawFromDeck(playerIndex : Int, cardCount : Int) extends Move
object DrawFromDeck {
  def initialHand(playerIndex : Int) = DrawFromDeck(playerIndex, 5)
}