package sa.ai.player.bot

import scala.util.Random
import sa.ai.player.{InfoSet, Player}
import sa.ai.rule.{Buy, Move}
import sa.ai.model.card.Card

/**
  *
  */
class Alex3Player(random : Random) extends WeightedMovePlayer(random)
{
  def weight(info: InfoSet, move : Move) : Double = {
    val endGame : Boolean =
      info.basic.province.cards.size < 5

    move match {
      case Buy(Card.Curse) =>
        -1000


      case Buy(Card.Estate) =>
        if (endGame) 10 else  -10


      case Buy(Card.Duchy) =>
        if (endGame) 100 else  -1

      case Buy(Card.Province) =>
        100000


      case Buy(Card.Copper) => -3
      case Buy(Card.Silver) => 4
      case Buy(Card.Gold)   => 100

      case _ => 0.01
    }
  }
}
