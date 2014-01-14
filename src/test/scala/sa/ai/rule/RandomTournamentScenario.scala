package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.player.{TournamentOutcome, GameOutcome, Tournament, Player, RandomPlayer}
import com.google.common.math.IntMath
import scala.util.Random

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
      1
    
    "Have a distinct number of playouts" in {
      playouts must be greaterThan 0
    }

    "Have an outcome" in {
      val outcome : TournamentOutcome =
        Tournament.play(players, playouts, OfficialRuleset(RandomShuffler(new Random(seed))))

      
      "With a winner" in {
        outcome.winner must be between(0, players.size - 1)
      }
    }
  }
}
