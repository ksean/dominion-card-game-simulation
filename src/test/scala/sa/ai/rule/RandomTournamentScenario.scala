package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.player.{TournamentOutcome, GameOutcome, Tournament, Player}
import com.google.common.math.IntMath
import scala.util.Random
import sa.ai.player.bot.RandomPlayer

/**
 * 
 */
class RandomTournamentScenario extends SpecificationWithJUnit {
  "A two player random tournament" should {
    val seed : Int =
      IntMath.pow(420, 2)
    
    val players : Seq[Player] =
      Seq.fill(2)(new RandomPlayer(new Random(seed)))

    "Consist of two random players" in {
      players.size must be equalTo 2
    }
    
    val playouts : Int =
      10000
    
    "Have a distinct number of playouts" in {
      playouts must be greaterThan 0
    }

    "Have an outcome" in {
      val outcome : TournamentOutcome =
        Tournament.play(players, playouts, OfficialRuleset(RandomShuffler(new Random(seed))))

      print(outcome)

      
      "With a winner" in {
        outcome.winner must be between(0, players.size - 1)
      }

      "Where the number of wins of both players combiner" in {
        val wins : Seq[Int] =
          outcome.wins

        "Is at least as many as the number of playouts" in {
          wins.sum must be greaterThanOrEqualTo playouts
        }


      }
    }
  }
}
