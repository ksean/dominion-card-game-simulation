package sa.ai

import sa.ai.model.{Dominion, Game}
import sa.ai.model.card.{Kingdom, Basic, Pile, Card}
import sa.ai.view.{FxView, ViewUtils, ConsoleView}
import java.util
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.ScrollPane

/**
 * Entry point
 */
object DominionGameMain extends JFXApp
{
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
