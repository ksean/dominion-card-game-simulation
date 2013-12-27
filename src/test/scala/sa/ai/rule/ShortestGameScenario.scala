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
    OfficialRuleset(
      cardSequenceShuffler(
        // 1st player before the game draw
        Card.Copper -> 4, Card.Estate -> 1,
          Card.Copper -> 3, Card.Estate -> 2,

        // 2nd player before the game draw
        Card.Copper -> 3, Card.Estate -> 2,
          Card.Copper -> 4, Card.Estate -> 1,

        // 1st player 2nd cleanup draw
        Card.Copper -> 3, Card.Estate -> 2,
          Card.Copper -> 4, Card.Estate -> 1,
          Card.Silver -> 2, // silver is partial, continued in 3rd draw

        // 2nd player 2nd cleanup draw
        Card.Copper -> 3, Card.Estate -> 2,
          Card.Copper -> 4, Card.Estate -> 1,

        // 1st player 3rd draw
        Card.Silver -> 1, Card.Copper -> 2,
          // the rest are arbitrary
        Card.Silver -> 3, Card.Copper -> 9,
//          Card.Copper -> 9,

        // 2nd player 3rd draw - arbitrary
        Card.Copper -> 3, Card.Estate -> 2, Card.Copper -> 4, Card.Estate -> 1
      ))


  def cardSequenceShuffler(cardTypeCounts : (Card, Int)*) : Shuffler =
    new LiteralShuffler(
      cardTypeCounts.flatMap(c => Seq.fill(c._2)(c._1)))

  "The two player shortest game winning scenario" should {
    val firstDraw =
      Game
        .twoPlayerFirstAction(rules)
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
      "Have the first player" in {
        val firstPlayerThirdTurn =
          afterBothPlayersSecondTurns.players(0)

        "Own 2 silvers" in {
          val firstPlayerCards =
            firstPlayerThirdTurn.cards

          val firstPlayerSilvers = firstPlayerCards.count(_ == Card.Silver)
          firstPlayerSilvers must be equalTo 2
        }
        "Discard pile consist of nothing" in {
          val discarded : Seq[Card] =
            firstPlayerThirdTurn.discard.cards

          discarded.size must be equalTo 0
        }
        "Hand consist of 3 coppers and 2 estates" in {
          val thirdHand : Seq[Card] =
            firstPlayerThirdTurn.hand.cards

          thirdHand.count(_ == Card.Copper) must be equalTo 3
          thirdHand.count(_ == Card.Estate) must be equalTo 2
        }
      }
    }


    val afterBothPlayersThirdTurns : Game =
      rules.transition(
        afterBothPlayersSecondTurns,
        Seq(NoAction, Buy(Card.Silver), NoBuy, CleanupAction) ++
          Seq(NoAction, NoBuy, CleanupAction)
      )


    "After both players' third turns" in {
      "Have the first player" in {
        "Be next to act" in {
          afterBothPlayersThirdTurns.nextToAct must be equalTo 0
        }

        val firstPlayerFourthTurn =
          afterBothPlayersThirdTurns.players(0)

        "Own 3 silvers" in {
          val firstPlayerSilvers = firstPlayerFourthTurn.cards.count(_ == Card.Silver)
          firstPlayerSilvers must be equalTo 3
        }
      }
    }


    val afterBothPlayersFourthTurns : Game =
      rules.transition(
        afterBothPlayersThirdTurns,
        Seq(NoAction, NoBuy, CleanupAction) ++
          Seq(NoAction, NoBuy, CleanupAction)
      )

    "On the last action of the game" in {
      "First player" in {
        val firstPlayer =
          afterBothPlayersFourthTurns.players(0)

        "Is next to act" in {
          afterBothPlayersFourthTurns.nextToAct must be equalTo 0
        }

        "Hand has 3 silvers and 2 coppers" in {
          val lastActionHand : Seq[Card] =
            firstPlayer.hand.cards

          lastActionHand.count(_ == Card.Silver) must be equalTo 3
          lastActionHand.count(_ == Card.Copper) must be equalTo 2
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
