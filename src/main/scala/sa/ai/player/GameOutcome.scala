package sa.ai.player

import sa.ai.model.Game

/**
 *
 */
case class GameOutcome(
  states : Seq[Game]
) {
  def terminalState : Game =
    states.last
  
  def winners : Set[Int] =
    states.last.currentlyWinningPlayers()
}
