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
)

object Dominion {
  def getWealth(dominion:Dominion) : Int = {
    val inPlayCardValues = dominion.inPlay.cards.map(_.value).toList
    //http://stackoverflow.com/questions/12496959/summing-values-in-a-list
    def sum(xs: List[Int]): Int = {
      @tailrec
      def inner(xs: List[Int], accum: Int): Int = {
        xs match {
          case x :: tail => inner(tail, accum + x)
          case Nil => accum
        }
      }
      inner(xs, 0)
    }
    return sum(inPlayCardValues)
  }
  def getBuys(dominion:Dominion) : Int = {
    val dominionBuys = 1
    return dominionBuys
  }
  val initialState = {
    val coppers = Seq.fill(7)(Card.Copper)
    val estates = Seq.fill(3)(Card.Estate)

    val deck = Deck(Seq())

    Dominion(DiscardPile(coppers ++ estates), deck)
  }
}