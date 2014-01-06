package sa.ai.player

import sa.ai.model.Game

/**
 *
 */
case class Outcome(
  states : Seq[Game]
) {
  def terminalState : Game =
    states.last
}
