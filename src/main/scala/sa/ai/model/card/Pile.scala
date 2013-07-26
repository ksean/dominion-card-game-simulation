package sa.ai.model.card

/**
 * Dominion Card pile
 */

sealed trait Pile {
  def cards : Seq[Card]
}


case class SupplyPile(
    card : Card,
    size : Int
)
  extends Pile
{
  def cards =
    List.fill(size)(card)
}


case class TrashPile(cards: Seq[Card])
  extends Pile


case class DiscardPile(cards: Seq[Card])
  extends Pile

case class Deck(cards: Seq[Card])
  extends Pile

case class Hand(cards: Seq[Card])
  extends Pile
object Hand {
  val empty = Hand(Seq.empty)
}


object Pile {
  def apply(cardType : Card, pileSize : Int):Pile =
    SupplyPile(cardType, pileSize)
}