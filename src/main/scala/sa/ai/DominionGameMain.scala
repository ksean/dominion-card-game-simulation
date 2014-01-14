package sa.ai

import sa.ai.model.{Dominion, Game}
import sa.ai.model.card.{Kingdom, Basic, Pile, Card}
import sa.ai.view.{FxView, ViewUtils, ConsoleView}
import java.util
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.ScrollPane
import scala.util.Random
import sa.ai.player.{GameOutcome, Playout, RandomPlayer, Player}
import sa.ai.rule.{Shuffler, RandomShuffler, OfficialRuleset}
import com.google.common.collect._
import scala.collection.JavaConversions._
import sa.ai.rule.OfficialRuleset
import sa.ai.player.GameOutcome
import sa.ai.rule.RandomShuffler

/**
 * Entry point
 */
object DominionGameMain extends JFXApp
{
  val random : Random =
    new Random()

  val counts : Multiset[Int] = HashMultiset.create()
//  val counts : ArrayTable[Set[Int], Int] = ArrayTable.create()

  for (i <- 1 to 100000000) {
    if (i % 1000 == 0) {
      println(i)
    }

    val outcome : GameOutcome =
      Playout.playToCompletion(
        Game.twoPlayerRestrictedInitialState,
        OfficialRuleset(RandomShuffler(random)),
        Seq.fill(2)(new RandomPlayer(random)))

    outcome.winners
    counts.add(outcome.states.size)


    if (i % 1000 == 0) {
      println(s"\n\n$i")
      counts.entrySet()
        .toSeq.sortBy(_.getElement)
        .map(e => s"${e.getElement}\t${e.getCount}")
        .foreach(println)
      println("\n\n")
    }
  }


  val state =
    Game.twoPlayerInitialState

  stage = new PrimaryStage {
    title = "Dominion"
    width = 600
    height = 750

    scene = new Scene {
      root =
        new ScrollPane {
          fitToWidth = true
          fitToHeight = true

          content =
            ViewUtils.indent(
              ViewUtils.labeled(
                "Dominion State:",
                FxView.stateView(state)
              ),
              top = 10,
              left = 10
            )
        }
    }
  }
}
