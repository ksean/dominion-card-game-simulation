package sa.ai.player.bot

import scala.util.Random
import sa.ai.rule.{Buy, Move}
import sa.ai.player.{InfoSet, Player}
import sa.ai.model.card.Card

/**
 * 
 */
class SeanPlayer(val random : Random) extends Player
{
  def play(infoSet: InfoSet, actions: Set[Move]): Move = {
    val randomIndex : Int =
      random.nextInt(actions.size)

    val buyMoves : Set[Buy] =
      actions.flatMap {
        case buy : Buy => Some(buy)
        case _ => None
      }

    if (buyMoves.isEmpty) {
      actions.toSeq(randomIndex)
    } else {
      if (buyMoves.contains(Buy(Card.Province))) {
        Buy(Card.Province)
      } else {
        actions.toSeq(randomIndex)
      }
    }
  }
}
