package sa.ai.player.bot

import scala.util.Random
import sa.ai.player.{InfoSet, Player}
import sa.ai.rule.{Buy, Move}
import sa.ai.model.card.{CardType, Card}

/**
 *
 */
class AlexPlayer(random : Random) extends WeightedMovePlayer(random)
{
  def weight(info : InfoSet, move : Move) : Double =
    move match {
      case Buy(Card.Curse) => -10

      case Buy(Card.Estate)   => -3
      case Buy(Card.Duchy)    => 10
      case Buy(Card.Province) => 100

      case Buy(Card.Copper) => -3
      case Buy(Card.Silver) => 4
      case Buy(Card.Gold)   => 25

      case _ => 0.01
    }
}
