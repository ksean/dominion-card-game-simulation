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
import sa.ai.model.card.{SupplyPile, Card, Pile}

/**
 * 17/12/13 10:50 PM
 */
case object FxView extends JFXApp
{
  val state : Game =
    Game.twoPlayerInitialState

  def cardView(card: Card) : Node =
    new Label(s"${card.name}")

  def cardSeqView(cards: Seq[Card]) : Node =
    new HBox { content =
      ViewUtils.row(
        cards.map(cardView)
      )
    }

  def supplyPileView(pile: SupplyPile) : Node =
    new VBox { content =
      Seq(
        cardView(pile.card),
        new Label(s"# ${pile.size}")
      )
    }


  val stateView : Node = {
    val basicSupplyPiles : Node = {
      val basicSupplyView : Node =
        ViewUtils.row(
          state.basic.supply.toSeq.map(supplyPileView))

      val trashView : Node = {
        val trash = state.basic.trash.cards
        if (trash.isEmpty) {
          new Label("[Empty]")
        } else {
          ViewUtils.row(
            trash.map(cardView))
        }
      }

      new VBox { content =
        Seq(
          new Label("Basic Supply:"),
          basicSupplyView,
          new Label("Trash:"),
          trashView
        )
      }
    }

    val kingdomSupplyPiles : Node =
      new VBox {
        content = Seq(
          new Label("Kingdom:"),
          ViewUtils.row(
            state.kingdom.supply.toSeq.map(supplyPileView))
        )
      }

    new VBox {
      content = Seq(
        new Label(s"Next to act index: ${state.nextToAct}"),
        new Label(s"Phase is: ${state.phase}"),
        new Label(s"Provinces left: ${state.basic.province.size}"),
        new HBox { prefHeight = 5 },
        basicSupplyPiles,
        new HBox { prefHeight = 5 },
        kingdomSupplyPiles
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
