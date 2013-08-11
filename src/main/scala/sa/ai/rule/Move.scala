package sa.ai.rule

/**
 * 15/07/13 9:26 PM
 */
sealed abstract trait Move

case class ShuffleDiscardIntoDeck() extends Move


case class DrawFromDeck(cardCount : Int) extends Move
object DrawFromDeck {
  def initialHand = DrawFromDeck(5)
}


case object NoAction extends Move