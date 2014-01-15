package sa.ai.player.bot

import scala.util.Random
import sa.ai.rule.Move
import sa.ai.player.{InfoSet, Player}

/**
 * 
 */
class RandomPlayer(val random : Random) extends Player
{
  def play(infoSet: InfoSet, actions: Set[Move]): Move = {
    val randomIndex : Int =
      random.nextInt(actions.size)

    actions.toSeq(randomIndex)
  }
}
