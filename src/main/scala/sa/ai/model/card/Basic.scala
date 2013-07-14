package sa.ai.model.card

/**
 * http://dominioncg.wikia.com/wiki/Basic_Cards
 */
case class Basic(
  copper : Pile,
  silver : Pile,
  gold   : Pile
)

object Basic {
  val initialSetForTwoPlayers =
    Basic(
      copper = Pile(Card.Copper, 20),
      silver = Pile(Card.Silver, 20),
      gold = Pile(Card.Gold, 20)
    )

}