package sa.ai.player.bot

import scala.util.Random
import sa.ai.player.{InfoSet, Player}
import sa.ai.rule.{Buy, Move}
import sa.ai.model.card.Card

/**
 *
 */
class ShawnPlayer(val random : Random) extends Player
{
  def play(infoSet: InfoSet, actions: Set[Move]): Move =
    actions
      .map(action =>
      (action, score(infoSet, action)))
      .maxBy(_._2)
      ._1

  def score(info: InfoSet, move : Move) : Double =
    weight(info, move) * random.nextDouble()


  def weight(info: InfoSet, move : Move) : Double =
    move match {
      case Buy(card) =>
        card match {
          case Card.Curse => -100000

          case Card.Estate   => -100
          case Card.Duchy    => -1
          case Card.Province => 10000000

          case Card.Copper => -100
          case Card.Silver => 10
          case Card.Gold   => 1000

          case _ => 0.01
        }

      case _ => 0.01
    }
}
