package sa.ai.model

import sa.ai.model.card._
import scala.annotation.tailrec
import scala.Predef._

/**
 * Player's dominion
 */
case class Dominion(
  discard : DiscardPile,
  deck    : Deck,
  hand    : Hand   = Hand.empty,
  inPlay  : InPlay = InPlay.empty,
  buys    : Int    = 1,
  spent   : Int    = 0
) {
  assert(spent >= 0     , "Can't spend a negative amount"      )
  assert(spent <= wealth, "Can't spend more than what you have")


  def wealth : Int =
    inPlay.cards.map(_.value).sum

  def availableWealth : Int =
    wealth - spent

  def victoryPoints : Int =
    List(discard, deck, hand, inPlay)
      .flatMap(_.cards.map(_.victory)).sum



  def withBuys(buys: Int) : Dominion =
    copy(buys = buys)

  def withSpent(spent: Int) : Dominion =
    copy(spent = spent)
  
  def addDiscard(discarded: Card) : Dominion = {
    val nextDiscard : DiscardPile =
      discard.add(discarded)  
    
    copy(discard = nextDiscard)
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