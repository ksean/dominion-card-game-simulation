package sa.ai.rule

import sa.ai.model.card.{Pile, Card, Deck, DiscardPile}
import scala.util.Random
import scala.Predef._

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

trait ShufflerValidation extends Shuffler {
  def shuffle(discard: DiscardPile): Deck = {
    val shuffled = shuffleValidated(discard)
    
    def histogram(pile : Pile) : Map[Card, Int] =
      pile.cards.groupBy((card: Card) => card).mapValues(_.size)
    
    val before : Map[Card, Int] = histogram(discard)
    val after  : Map[Card, Int] = histogram(shuffled)

    def display(cardHistogram : Map[Card, Int]) : String =
      cardHistogram.map((cardCount: (Card, Int)) => (cardCount._1.name, cardCount._2)).toString()

    assert(before == after, s"Different cards: ${display(before)} | ${display(after)}")
    shuffled
  }
  
  def shuffleValidated(discard: DiscardPile): Deck
}


case class RandomShuffler(random : Random = new Random()) extends Shuffler with ShufflerValidation {
  def shuffleValidated(discard : DiscardPile): Deck =
    Deck(random.shuffle(discard.cards))
}


case object PassThroughShuffler extends Shuffler with ShufflerValidation {
  def shuffleValidated(discard: DiscardPile): Deck =
    Deck(discard.cards)
}


class LiteralByTurnShuffler(cards: Seq[Seq[Card]]) extends Shuffler with ShufflerValidation {
  private var offset: Int = 0
  
  def shuffleValidated(discard: DiscardPile): Deck = {
    val nextDeck =
      Deck(cards(offset))

    offset += 1

    nextDeck
  }
}


class LiteralShuffler(cards: Seq[Card]) extends Shuffler with ShufflerValidation {
  private var offset: Int = 0


  def shuffleValidated(discard: DiscardPile): Deck = {
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
