package sa.ai.player

import sa.ai.rule.Move

/**
 *
 */
trait Player
{
  def play(infoSet : InfoSet, actions : Set[Move]) : Move
}
