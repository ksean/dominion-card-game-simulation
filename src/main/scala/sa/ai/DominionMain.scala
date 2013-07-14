package sa.ai

import sa.ai.model.{Game}
import sa.ai.model.card.{Basic, Pile, Card}
import sa.ai.view.ConsoleView

/**
 * Entry point
 */
object DominionMain extends App
{
  val initialState =
    Basic(
      copper = Pile(Card.Copper, 20),
      silver = Pile(Card.Silver, 20),
      gold = Pile(Card.Gold, 20)
    )

  ConsoleView.display(initialState)


//  val firstGameCardNames =
//    Card.FirstGame.map(_.toString)
//
//  println(s"First game cards are: ${firstGameCardNames.mkString(", ")}")
}
