package sa.ai.rule

import sa.ai.model.{CleanupPhase, Game}
import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.card.Card
import sa.ai.rule.CleanupAction

/**
 * 08/12/13.
 */
class ShortestGameScenario extends SpecificationWithJUnit
{
  def copperEstateShuffler(coppers: Int, estates: Int) : Shuffler =
    LiteralShuffler(
      Seq.fill(coppers)(Card.Copper) ++
        Seq.fill(estates)(Card.Estate))

  "The two player shortest game winning scenario" should {
    val firstDraw =
      Game.twoPlayerFirstAction(copperEstateShuffler(4, 1))
        .withProvincesRemaining(1)

    "Have first draw" in {
      "With only 1 province remaining" in {
        firstDraw.basic.province.cards.size must be equalTo 1
      }

      "With 4 coppers and 1 estate" in {
        val firstHand = firstDraw.players(0).hand.cards
        firstHand.count(_ == Card.Copper) must be equalTo 4
      }
    }

    val afterFirstPlayerBuys : Game =
      Ruleset.transition(
        firstDraw,
        Seq(NoAction, Buy(Card.Silver), NoBuy))

    "After the first player buys" in {
      "Have the first player be in the cleanup phase" in {
        afterFirstPlayerBuys.phase must be equalTo CleanupPhase
      }
      "Where the only move is the cleanup action" in {
        val afterFirstPlayerMoves : Set[Move] = Ruleset.moves(afterFirstPlayerBuys)
        afterFirstPlayerMoves must be equalTo Set(CleanupAction)
      }
    }

//    "After the "

    val afterBothPlayersFirstBuys : Game =
      Ruleset.transition(
        afterFirstPlayerBuys,
        CleanupAction +: Seq(NoAction, NoBuy, CleanupAction))
    
    "After both players' first buys" in {
      "Have the first player own a silver" in {
        val firstPlayerSilvers = afterBothPlayersFirstBuys.players(0).cards.count(_ == Card.Silver)
        firstPlayerSilvers must be equalTo 1
      }
    }

    "After the first turns" in {
      ok
    }



   /* val afterSecondBuys : Game =
      Ruleset.transition(
        afterFirstBuys,
        Seq(
          NoAction, Buy(Card.Silver), NoBuy,
          NoAction, NoBuy))
    "After the second time each player buys" in {
      ok
    }*/
    
  }
}
