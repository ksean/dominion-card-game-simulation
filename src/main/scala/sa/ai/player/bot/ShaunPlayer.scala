package sa.ai.player.bot

import scala.util.Random
import sa.ai.player.{InfoSet, Player}
import sa.ai.rule.{Buy, Move}
import sa.ai.model.card.Card

/**
 *
 */
class ShaunPlayer(val random : Random) extends Player
{
  def play(infoSet: InfoSet, actions: Set[Move]): Move =
    actions
      .map(action =>
      (action, score(infoSet, action)))
      .maxBy(_._2)
      ._1

  def score(info: InfoSet, move : Move) : Double =
    weight(info, move) * random.nextDouble()


  def weight(info: InfoSet, move : Move) : Double = {
    val lastProvinces : Int =
      info.basic.province.size

    move match {
      case Buy(card) =>
        card match {
          case Card.Curse => -100000

          case Card.Estate   => if (lastProvinces >  5) -100 else 5
          case Card.Duchy    => if (lastProvinces >  5) -10 else 100
          case Card.Province => 1000000000/lastProvinces

          case Card.Copper => -100
          case Card.Silver => if (lastProvinces >  5) 10 else -1
          case Card.Gold   => if (lastProvinces >  5) 1000 else -1

          case _ => 0.01
        }

      case _ => 0.01
    }
  }
}
