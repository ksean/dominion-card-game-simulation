package sa.ai.rule

import sa.ai.model.card.{Deck, DiscardPile}
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


case class RandomShuffler(random : Random = new Random()) extends Shuffler
{
  def shuffle(discard : DiscardPile): Deck =
    Deck(random.shuffle(discard.cards))
}

case object PassThroughShuffler extends Shuffler {
  def shuffle(discard: DiscardPile): Deck =
    Deck(discard.cards)
}