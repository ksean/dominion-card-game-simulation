package sa.ai.rule

import sa.ai.model.{CleanupPhase, Game}
import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.card.Card

/**
 * 08/12/13.
 */
class ShortestGameScenario extends SpecificationWithJUnit
{
  val rules =
    OfficialRuleset()

  def cardSequenceShuffler(cardTypeCounts : (Card, Int)*) : Shuffler =
    LiteralShuffler(
      cardTypeCounts.flatMap(c => Seq.fill(c._2)(c._1)))

  "The two player shortest game winning scenario" should {
    val firstDraw =
      Game
        .twoPlayerFirstAction(
          cardSequenceShuffler(
            Card.Copper -> 4, Card.Estate -> 1,
            Card.Copper -> 3, Card.Estate -> 2))
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


    val afterFirstPlayerFirstActionPhase : Game =
      rules.transition(firstDraw, NoAction)

    "After the first player's first action phase" in {
      "The first player's hand must consist of a single estate" in {
        afterFirstPlayerFirstActionPhase.players(0).hand.cards must beEqualTo(Seq(Card.Estate))
      }
    }


    val afterFirstPlayerBuys : Game =
      rules.transition(
        afterFirstPlayerFirstActionPhase,
        Seq(Buy(Card.Silver), NoBuy))

    "After the first player buys" in {
      "Have the first player be in the cleanup phase" in {
        afterFirstPlayerBuys.phase must be equalTo CleanupPhase
      }
      "Where the only move is the cleanup action" in {
        val afterFirstPlayerMoves : Set[Move] = rules.moves(afterFirstPlayerBuys)
        afterFirstPlayerMoves must be equalTo Set(CleanupAction)
      }
    }

    val afterBothPlayersFirstTurns : Game =
      rules.transition(
        afterFirstPlayerBuys,
        CleanupAction +: Seq(NoAction, NoBuy, CleanupAction)
      )//(copperEstateShuffler(3, 2))
    
    "After both players' first turns" in {
      "Have the first player own a silver" in {
        val firstPlayerSilvers = afterBothPlayersFirstTurns.players(0).cards.count(_ == Card.Silver)
        firstPlayerSilvers must be equalTo 1
      }
      "Have the first player's discard pile consist of 4 coppers and 1 estate" in {
        val discarded : Seq[Card] =
          afterBothPlayersFirstTurns.players(0).discard.cards

        discarded.count(_ == Card.Copper) must beEqualTo(4)
        discarded must contain( Card.Estate )
      }

      "Have the first player's hand consist of 3 coppers and 2 estates" in {
        val secondHand : Seq[Card] =
          afterBothPlayersFirstTurns.players(0).hand.cards

        secondHand.count(_ == Card.Copper) must beEqualTo(3)
        secondHand.count(_ == Card.Estate) must beEqualTo(2)
      }

      val afterFirstPlayerSecondActionPhase : Game =
        rules.transition(afterBothPlayersFirstTurns, NoAction)

      "After the first player's second action phase" in {
        "The first player's hand must consist of two estates" in {
          afterFirstPlayerSecondActionPhase.players(0).hand.cards.count(_ == Card.Estate) must be equalTo 2
        }
      }


      val afterFirstPlayerSecondBuy : Game =
        rules.transition(
          afterFirstPlayerSecondActionPhase,
          Seq(Buy(Card.Silver), NoBuy))

      "After the first player's second buy" in {
        "Have the first player be in the cleanup phase" in {
          afterFirstPlayerSecondBuy.phase must be equalTo CleanupPhase
        }
        "Where the only move is the cleanup action" in {
          val secondTurnFirstPlayerMoves : Set[Move] = rules.moves(afterFirstPlayerSecondBuy)
          secondTurnFirstPlayerMoves must be equalTo Set(CleanupAction)
        }
      }

      val afterBothPlayersSecondTurns : Game =
        rules.transition(
          afterFirstPlayerSecondBuy,
          CleanupAction +: Seq(NoAction, NoBuy, CleanupAction)
        )

      "After both players' second turns" in {
        "Have the first player own 2 silvers" in {
          val firstPlayerSilvers = afterBothPlayersSecondTurns.players(0).cards.count(_ == Card.Silver)
          firstPlayerSilvers must be equalTo 2
        }
        "Have the first player's discard pile consist of nothing" in {
          val discarded : Seq[Card] =
            afterBothPlayersSecondTurns.players(0).discard.cards

          discarded.size must be equalTo 0
        }

        "Have the first player's hand consist of 3 coppers and 2 estates" in {
          val thirdHand : Seq[Card] =
            afterBothPlayersSecondTurns.players(0).hand.cards

          thirdHand.count(_ == Card.Copper) must beEqualTo(3)
          thirdHand.count(_ == Card.Estate) must beEqualTo(2)
        }
      }
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
