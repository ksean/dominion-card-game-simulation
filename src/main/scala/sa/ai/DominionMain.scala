package sa.ai

import sa.ai.model.Game
import sa.ai.model.card.{Kingdom, Basic, Pile, Card}
import sa.ai.view.ConsoleView

/**
 * Entry point
 */
object DominionMain extends App
{
  val state =
    Game(
      Basic.initialSetForTwoPlayers,
      Kingdom.firstGame
    )

  ConsoleView.display(state)
}
