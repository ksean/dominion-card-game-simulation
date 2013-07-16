package sa.ai.view

import sa.ai.model.card.{Basic, Kingdom}
import sa.ai.model.Game

/**
 * 13/07/13 5:36 PM
 */
object ConsoleView
{
  def display(game:Game) {
    println("Basic cards: ")
    display(game.basic)

    println("\n\nKingdom cards: ")
    display(game.kingdom)
  }

  def display(basic:Basic) {
    println(basic.copper)
    println(basic.silver)
    println(basic.gold)
    println(basic.estate)
    println(basic.duchy)
    println(basic.province)
    println(basic.trash)
  }

  def display(kingdom:Kingdom) {
    kingdom.supply.foreach(println)
  }
}
