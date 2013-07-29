package sa.ai

import sa.ai.model.{Dominion, Game}
import sa.ai.model.card.{Kingdom, Basic, Pile, Card}
import sa.ai.view.ConsoleView

/**
 * Entry point
 */
object DominionGameMain extends App
{
  val state =
    Game.twoPlayerInitialState

  ConsoleView.display(state)
}
