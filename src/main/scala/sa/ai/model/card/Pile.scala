package sa.ai.model.card

/**
  * Dominion Card pile
 */

sealed trait Pile {
  def cards : Seq[Card]
}

object Pile {
  def apply(cardType : Card, pileSize : Int): SupplyPile =
    SupplyPile(cardType, pileSize)
}



case class SupplyPile(
  card : Card,
  size : Int
) extends Pile {
  def cards =
    Seq.fill(size)(card)

  def isEmpty =
    card == null
}


object SupplyPile{
  val empty = SupplyPile(null, 0)
}




case class TrashPile(cards: Seq[Card])
  extends Pile

object TrashPile{
  val empty = TrashPile(Seq.empty)
}



case class DiscardPile(cards: Seq[Card])
  extends Pile

object DiscardPile{
  val empty = DiscardPile(Seq.empty)
}



case class Deck(cards: Seq[Card])
  extends Pile

object Deck{
  val empty = Deck(Seq.empty)
}



case class Hand(cards: Seq[Card])
  extends Pile

object Hand {
  val empty = Hand(Seq.empty)
}



case class InPlay(cards: Seq[Card])
  extends Pile

object InPlay{
  val empty = InPlay(Seq.empty)
}