package sa.ai.player.bot

import scala.util.Random
import sa.ai.player.{InfoSet, Player}
import sa.ai.rule.Move

/**
 *
 */
abstract class WeightedMovePlayer(val random : Random) extends Player
{
  def play(infoSet: InfoSet, actions: Set[Move]): Move =
    actions
      .map(action =>
        (action, score(infoSet, action)))
      .maxBy(_._2)
      ._1

  def score(info: InfoSet, move : Move) : Double =
    weight(info, move) * random.nextDouble()


  def weight(info: InfoSet, move : Move) : Double
}
