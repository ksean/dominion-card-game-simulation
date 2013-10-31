package sa.ai.builder

import org.specs2.mutable.SpecificationWithJUnit
import sa.ai.model.Game

/**
 * Date: 30/10/13
 * Time: 8:16 PM
 */
class GameSpec extends SpecificationWithJUnit {
  "Game" should {
    val game = Game.empty

    "Start in the empty state" in {
      "Where there are no provinces remaining" in {
        game.basic.province.cards must beEmpty
      }
    }

    "Can have its supply changed" in {
      "Where the provinces can be set to 42" in {
        val withOneProvince =
          game.withProvincesRemaining(42)

        withOneProvince.basic.province.cards.size should be equalTo 42
      }


    }
  }
}