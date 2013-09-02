package sa.ai.model

import sa.ai.model.card._
import sa.ai.model.card.DiscardPile
import sa.ai.model.card.Deck
import scala.annotation.tailrec

/**
 * Player's dominion
 */
case class Dominion(
  discard : DiscardPile,
  deck : Deck,
  hand : Hand = Hand.empty,
  inPlay : InPlay = InPlay.empty
) {
  def wealth : Int =
    inPlay.cards.map(_.value).sum

  def buys : Int = {
    val dominionBuys = 1
    dominionBuys
  }
}

object Dominion {
  val initialState = {
    val coppers = Seq.fill(7)(Card.Copper)
    val estates = Seq.fill(3)(Card.Estate)

    val deck = Deck(Seq())

    Dominion(DiscardPile(coppers ++ estates), deck)
  }
}