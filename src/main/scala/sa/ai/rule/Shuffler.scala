package sa.ai.rule

import sa.ai.model.card.{Card, Deck, DiscardPile}
import scala.util.Random

/**
 *
 */
trait Shuffler {
  def shuffle(discard : DiscardPile): Deck
}

object Shuffler {
  implicit val passThrough : Shuffler =
    PassThroughShuffler
}


case class RandomShuffler(random : Random = new Random()) extends Shuffler {
  def shuffle(discard : DiscardPile): Deck =
    Deck(random.shuffle(discard.cards))
}


case object PassThroughShuffler extends Shuffler {
  def shuffle(discard: DiscardPile): Deck =
    Deck(discard.cards)
}


class LiteralShuffler(cards: Seq[Card]) extends Shuffler {
  private var offset: Int = 0


  def shuffle(discard: DiscardPile): Deck = {
    val discardSize : Int =
      discard.cards.size

    {
      val requiredCards : Int =
        offset + discardSize

      assert(requiredCards <= cards.size,
        s"Must specify enough cards ($requiredCards) to shuffle ${cards.size}")
    }

    val offsetCards : Seq[Card] =
      cards.drop(offset)

    val shuffledCards : Seq[Card] =
      offsetCards.take(discardSize)

    offset += discardSize

    Deck(shuffledCards)
  }
}
