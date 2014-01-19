package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.player.{Tournament, TournamentOutcome, Playout, Player}
import scala.util.Random
import sa.ai.model.Game
import sa.ai.player.bot.{Alex2Player, RandomPlayer}
import com.google.common.math.IntMath

/**
 *
 */
class RandomTournamentSpec extends SpecificationWithJUnit
{
  "A random tournament" should {

    val playouts : Int =
      1000

    val tolerance : Double =
      0.01

    val seed : Int =
      IntMath.pow(420, 3)

    val players : Seq[Player] =
      Seq.fill(2)(new Alex2Player(new Random(seed)))

    "Where each player wins a number of times" in {
      val outcome : TournamentOutcome =
        Tournament.play(players, playouts, OfficialRuleset(RandomShuffler(new Random(seed))))

      "Within the margin of tolerance" in {
        val wins : Seq[Int] =
          outcome.wins

        val deltaWins : Int =
          math.abs(wins(0) - wins(1))

        val maxDelta : Int =
          (playouts * tolerance).toInt


        maxDelta must beGreaterThanOrEqualTo(deltaWins)
      }
    }
  }
}
