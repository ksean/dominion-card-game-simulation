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
    val stage =
      Stage.fromProvincesRemaining(
        info.basic.province.cards.size)

    move match {
      case Buy(Card.Curse) =>
        -1000


      case Buy(Card.Estate) =>
        stage match {
          case Beginning => -10
          case Middle    => -10
          case End       => 100
        }

      case Buy(Card.Duchy) =>
        stage match {
          case Beginning => -1
          case Middle    => 150
          case End       => 10000
        }

      case Buy(Card.Province) =>
        1000000


      case Buy(Card.Copper) =>
        stage match {
          case Beginning => 0
          case Middle    => -3
          case End       => -3
        }

      case Buy(Card.Silver) =>
        stage match {
          case Beginning => 1000
          case Middle    => 100
          case End       => -1
        }

      case Buy(Card.Gold) =>
        stage match {
          case Beginning => 10000
          case Middle    => 1000
          case End       => 0
        }

      case _ => 0
    }
  }


  sealed trait Stage
  object Stage {
    def fromProvincesRemaining(provincesRemaining : Int) : Stage = {
      if (provincesRemaining >= 7) {
        Beginning
      } else if (provincesRemaining >= 5) {
        Middle
      } else {
        End
      }
    }
  }

  case object Beginning extends Stage
  case object Middle    extends Stage
  case object End       extends Stage
}
