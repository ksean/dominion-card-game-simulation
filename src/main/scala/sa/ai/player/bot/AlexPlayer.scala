package sa.ai.player.bot

import scala.util.Random
import sa.ai.player.{InfoSet, Player}
import sa.ai.rule.{Buy, Move}
import sa.ai.model.card.{CardType, Card}

/**
 *
 */
class AlexPlayer(val random : Random) extends Player
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
          case Card.Curse => -10

          case Card.Estate   => -3
          case Card.Duchy    => 10
          case Card.Province => 100

          case Card.Copper => -3
          case Card.Silver => 4
          case Card.Gold   => 25

          case _ => 0
        }

      case _ => 0
    }
}
