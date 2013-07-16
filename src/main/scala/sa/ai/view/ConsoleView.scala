package sa.ai.view

import sa.ai.model.card.{Basic, Kingdom}
import sa.ai.model.{Player, Game}

/**
 * Display game state to command-line console
 */
object ConsoleView
{
  def display(game:Game) {
    println("Players: ")
    game.players.foreach(display)

    println("\n\nBasic cards: ")
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

  def display(player:Player) {
    println(player.discard)
  }
}
