package sa.ai

import scala.util.Random
import com.google.common.collect.{HashMultiset, Multiset}
import sa.ai.player.{Player, Playout, GameOutcome}
import sa.ai.model.Game
import sa.ai.rule.{RandomShuffler, OfficialRuleset}
import scala.collection.JavaConversions._
import sa.ai.player.bot.{AlexPlayer, SeanPlayer, RandomPlayer}

/**
 *
 */
object DominionGameBatch extends App
{
  val random : Random =
    new Random()

  val counts : Multiset[Int] = HashMultiset.create()

  for (i <- 1 to 1000000) {
    if (i % 100 == 0) {
      println(i)
    }

    val playerA : Player =
      new SeanPlayer(random)
//      new AlexPlayer(random)
//      new RandomPlayer(random)

    val playerB : Player =
//      new SeanPlayer(random)
      new AlexPlayer(random)
//      new RandomPlayer(random)

    val outcome : GameOutcome =
      Playout.playToCompletion(
        Game.twoPlayerRestrictedInitialState,
        OfficialRuleset(RandomShuffler(random)),
        Seq(playerA, playerB))

//    counts.add(outcome.states.size)
    counts.addAll(outcome.winners)

    if (i % 1000 == 0) {
      println(s"\n\n$i")
      counts.entrySet()
        .toSeq.sortBy(_.getElement)
        .map(e => s"${e.getElement}\t${e.getCount}")
        .foreach(println)
      println("\n\n")
    }
  }


}
