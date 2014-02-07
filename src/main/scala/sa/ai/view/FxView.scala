package sa.ai.view

import scalafx.scene.Node
import scalafx.scene.layout.{VBox, HBox}
import scalafx.scene.control.Label
import sa.ai.model.{Dominion, Game}
import sa.ai.model.card._

/**
 * Visualize a Dominion game state.
 */
object FxView
{
  def cardView(card: Card) : Node =
    new Label(s"${card.name}")

  def cardSeqView(cards: Seq[Card]) : Node =
    if (cards.isEmpty) {
      new Label("[Empty]")
    } else {
      ViewUtils.row(
        cards.map(cardView))
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
      cardSeqView(dominion.inPlay.cards)

    val handCards : Node =
      cardSeqView(dominion.hand.cards)

    val deckCards : Node =
      cardSeqView(dominion.deck.cards)

    val discardCards : Node =
      cardSeqView(dominion.discard.cards)

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

      val trashView : Node =
        cardSeqView(state.basic.trash.cards)

      new VBox { content =
        ViewUtils.labeled(
          "Basic:",
          ViewUtils.column(Seq(
            ViewUtils.labeled("Supply:", basicSupplyView),
            ViewUtils.labeled("Trash:", trashView)
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
            new Label(s"Phase: ${state.phase}"))
          },
          basicSupplyPiles,
          kingdomSupplyPiles,
          ViewUtils.labeled(
            "Player Dominions:",
            dominionSeqView(state.dominions))
        ))
    }
  }
}
