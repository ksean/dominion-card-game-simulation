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

  def plusSize(addend: Int) : SupplyPile =
    copy(size = size + addend)

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
{
  def add(card : Card) : DiscardPile =
    DiscardPile(cards :+ card)

  def add(additionalCards : Traversable[Card]) : DiscardPile =
    DiscardPile(cards ++ additionalCards)

  def add(pile : Pile) : DiscardPile =
    add(pile.cards)

}

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
{
  def remove(cardsToRemove : Seq[Card]) : Hand =
    Hand(cards.diff(cardsToRemove))
}

object Hand {
  val empty = Hand(Seq.empty)
}



case class InPlay(cards: Seq[Card])
  extends Pile
{
  def add(additionalCards: Seq[Card]) : InPlay =
    InPlay(cards ++ additionalCards)
}

object InPlay{
  val empty = InPlay(Seq.empty)
}