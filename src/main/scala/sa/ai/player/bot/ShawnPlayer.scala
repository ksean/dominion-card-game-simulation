package sa.ai.player.bot

import scala.util.Random
import sa.ai.player.{InfoSet, Player}
import sa.ai.rule.{Buy, Move}
import sa.ai.model.card.{CardType, Card}

/**
 *
 */
class ShawnPlayer(val random : Random) extends Player
{
  def play(infoSet: InfoSet, actions: Set[Move]): Move =
    actions
      .map(action =>
      (action, score(action)))
      .maxBy(_._2)
      ._1

  def score(move : Move) : Double =
    weight(move) * random.nextDouble()


  def weight(move : Move) : Double =
    move match {
      case Buy(card) =>
        card match {
          case Card.Curse => -100000

          case Card.Estate   => -300
          case Card.Duchy    => 50
          case Card.Province => 1000000

          case Card.Copper => 0.01
          case Card.Silver => 100
          case Card.Gold   => 10000

          case _ => 0.01
        }

      case _ => 0.01
    }
}
