package sa.ai.view

import sa.ai.model.card.{Common, Kingdom}

/**
 * 13/07/13 5:36 PM
 */
object ConsoleView
{
  def display(common:Common) {
    println(common.copperCards)
    println(common.silverCards)
    println(common.goldCards)
  }
}
