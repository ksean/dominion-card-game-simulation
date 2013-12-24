package sa.ai.view

import scalafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import scalafx.scene.{Node, Scene}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.layout.{Pane, VBox, HBox}
import scalafx.geometry.Insets
import scalafx.scene.shape.{Arc, Circle}
import scalafx.scene.control.{ScrollPane, Label, ComboBox}
import sa.ai.model.{Dominion, Game}
import sa.ai.model.card._

/**
 * Visualize a Dominion game state.
 */
object FxView extends JFXApp
{
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

  def dominionView(dominion : Dominion) : Node = {
    val attributes : Node =
      new HBox {
        content = ViewUtils.row(Seq(
          new Label(s"Buys: ${dominion.buys}"),
          new Label(s"Wealth: ${dominion.wealth}")
        ))
      }

    val inPlayCards : Node =
      ViewUtils.row(
          dominion.inPlay.cards.map(cardView))

    val handCards : Node =
      ViewUtils.row(
        dominion.hand.cards.map(cardView))

    val deckCards : Node =
      ViewUtils.row(
        dominion.deck.cards.map(cardView))

    val discardCards : Node =
      ViewUtils.row(
        dominion.discard.cards.map(cardView))

    ViewUtils.column(Seq(
      attributes,
      ViewUtils.labeled("In Play:", inPlayCards),
      ViewUtils.labeled("Hand:", handCards),
      ViewUtils.labeled("Deck:", deckCards),
      ViewUtils.labeled("Discard:", discardCards)
    ))
  }

  def dominionSeqView(dominions : Seq[Dominion]) : Node = {
    ViewUtils.column(
      dominions.zipWithIndex.map((dominionWithIndex: (Dominion, Int)) => {
        val (dominion, index) = dominionWithIndex

        ViewUtils.labeled(
          s"Player # $index:",
          dominionView(dominion)
        )
      })
    )
  }


  def stateView(state : Game) : Node = {
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
        ViewUtils.labeled(
          "Basic:",
          ViewUtils.column(Seq(
            ViewUtils.labeled(
              "Supply:",
              basicSupplyView),
            ViewUtils.labeled(
              "Trash:",
              trashView)
          ))
        )
      }
    }

    val kingdomSupplyPiles : Node =
      ViewUtils.labeled(
        "Kingdom:",
        ViewUtils.row(
          state.kingdom.supply.toSeq.map(supplyPileView)))

    new VBox {
      content =
        ViewUtils.column(Seq(
          new VBox { content = Seq(
            new Label(s"Next to act index: ${state.nextToAct}"),
            new Label(s"Phase is: ${state.phase}"),
            new Label(s"Provinces left: ${state.basic.province.size}"))
          },
          basicSupplyPiles,
          kingdomSupplyPiles,
          ViewUtils.labeled(
            "Player Dominions:",
            dominionSeqView(state.players))
        ))
    }
  }
}
