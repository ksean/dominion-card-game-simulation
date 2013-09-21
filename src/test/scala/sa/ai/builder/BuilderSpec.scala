package sa.ai.builder

import org.specs2.mutable.SpecificationWithJUnit

/**
 * Date: 08/09/13
 * Time: 8:39 PM
 */
class BuilderSpec extends SpecificationWithJUnit {
  "The game builder" should {
    val gameBuilder = gameBuilder()

    "Be given a list of conditions" in {
      "With equal to or fewer than than the number of possible conditions" in {
      }
      "Where an empty list" in {
        "Results in an empty game state" in {
        }
      }
    }
  }
}
