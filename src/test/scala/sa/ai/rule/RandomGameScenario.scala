package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.player.{Playout, Player}
import scala.util.Random
import sa.ai.model.Game
import sa.ai.player.bot.RandomPlayer

/**
 *
 */
class RandomGameScenario extends SpecificationWithJUnit
{
  val random : Random =
    new Random()

  val rules : Ruleset =
    OfficialRuleset(RandomShuffler(random))


  "The two-player random game scenario" should {

    val randomPlayers : Seq[Player] =
      Seq.fill(2)(new RandomPlayer(random))

    "Reach a terminal state" in {
      val terminalState = Playout.playToCompletion(
        Game.twoPlayerRestrictedInitialState, rules, randomPlayers).terminalState

      "Where there are one or more winning players" in {
        val winningPlayers : Set[Int] = terminalState.currentlyWinningPlayers()
          winningPlayers.size must not be equalTo(0)
      }
    }
  }
}
