package sa.ai.rule

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.{AfterTheGamePhase, Game}

/**
 * First Cleanup Phase Spec
 */
class FirstCleanupSpec extends SpecificationWithJUnit {
  "The first cleanup phase" should {
    val inCleanupPhase = Game.twoPlayerFirstCleanup
    inCleanupPhase.phase must be equalTo AfterTheGamePhase

    "Have a set of moves" in {
      val movesInCleanupPhase : Set[Move] =
        Ruleset.moves(inCleanupPhase)

      "Where the player always moves their hand into the discard pile" in {
        movesInCleanupPhase must contain(PutHandIntoDiscard)
      }
    }
  }
}
