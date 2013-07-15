package sa.ai.view

import sa.ai.model.card.{Basic, Kingdom}

/**
 * 13/07/13 5:36 PM
 */
object ConsoleView
{
  def display(common:Basic) {
    println(common.copper)
    println(common.silver)
    println(common.gold)
    println(common.estate)
    println(common.duchy)
    println(common.province)
  }
}
