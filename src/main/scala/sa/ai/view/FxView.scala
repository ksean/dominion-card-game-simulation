package sa.ai.view

import scalafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import scalafx.scene.{Node, Scene}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.layout.{VBox, HBox}
import scalafx.geometry.Insets
import scalafx.scene.shape.{Arc, Circle}
import scalafx.scene.control.{Label, ComboBox}
import sa.ai.model.Game

/**
 * 17/12/13 10:50 PM
 */
case object FxView extends JFXApp
{
  val state : Game =
    Game.twoPlayerInitialState

  val comboBox = new ComboBox() {
    editable = true
  }

  val stateView : Node = {
    val supplyPiles : Seq[Node] =
      state.basic.supply.toSeq.flatMap(supply => {
        Seq(
          new Label(s"${supply.card.name}: ${supply.cards.size}"),
          new HBox {prefWidth = 10}
        )
      })

    new VBox {
      content = Seq(
        new Label(s"Next to act index: ${state.nextToAct}"),
        new Label(s"Phase is: ${state.phase}"),
        new Label(s"Provinces left: ${state.basic.province.size}"),
        new HBox { prefHeight = 5 },
        new HBox {
          content = supplyPiles
        }
      )
    }
  }

  stage = new PrimaryStage {
    width = 800
    height = 600
    scene = new Scene {
      content = Seq(
        new VBox {
          content = Seq(
            new Label("Dominion state:"),
            new HBox {
              content = Seq(
                new HBox {
                  prefWidth = 10
                },
                stateView
              )
            }
          )
        }
      )
    }
  }
}
