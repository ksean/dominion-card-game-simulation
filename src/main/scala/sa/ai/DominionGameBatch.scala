package sa.ai

import scala.util.Random
import com.google.common.collect.{HashMultiset, Multiset}
import sa.ai.player._
import sa.ai.model.Game
import sa.ai.rule.{RandomShuffler, OfficialRuleset}
import scala.collection.JavaConversions._
import sa.ai.player.bot._
import sa.ai.rule.OfficialRuleset
import sa.ai.rule.RandomShuffler

/**
 *
 */
object DominionGameBatch extends App
{
  val random : Random =
    new Random()

  var runningOutcome : Option[TournamentOutcome] =
    None

  for (i <- 1 to 100000000)
  {
    val playerA : Player =
//      new SeanPlayer(random)
//      new AlexPlayer(random)
//      new Alex2Player(random)
      new Alex3Player(random)
//      new RandomPlayer(random)

    val playerB : Player =
//      new SeanPlayer(random)
//      new ShawnPlayer(random)
      new ShaunPlayer(random)
//      new AlexPlayer(random)
//      new Alex2Player(random)
//      new RandomPlayer(random)

    val playouts : Int =
      1000

    val tournamentOutcome : TournamentOutcome =
      Tournament.play(Seq(playerA,playerB), playouts, OfficialRuleset(RandomShuffler(random)))

    runningOutcome =
      runningOutcome match {
        case None => Some(tournamentOutcome)
        case Some(tally) => Some(tally.plus(tournamentOutcome))
      }

    println(runningOutcome.map(o => o.winner + "\t" + o.wins + "\t" + o.victoryMargin * 1000).get)
  }
}
