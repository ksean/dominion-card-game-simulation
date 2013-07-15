package sa.ai

import sa.ai.model.Game
import sa.ai.model.card.{Basic, Pile, Card}
import sa.ai.view.ConsoleView

/**
 * Entry point
 */
object DominionMain extends App
{
  val initialState =
    Basic(
      copper = Pile(Card.Copper, 46),
      silver = Pile(Card.Silver, 40),
      gold = Pile(Card.Gold, 30),
      estate = Pile(Card.Estate, 24),
      duchy = Pile(Card.Estate, 12),
      province = Pile(Card.Estate, 12),
      curse = Pile(Card.Curse, 30)
    )

  ConsoleView.display(initialState)


//  val firstGameCardNames =
//    Card.FirstGame.map(_.toString)
//
//  println(s"First game cards are: ${firstGameCardNames.mkString(", ")}")
}
