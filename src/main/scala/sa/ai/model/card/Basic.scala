package sa.ai.model.card

/**
 * http://dominioncg.wikia.com/wiki/Basic_Cards
 */
case class Basic(
  copper   : Pile,
  silver   : Pile,
  gold     : Pile,
  estate   : Pile,
  duchy    : Pile,
  province : Pile,
  curse    : Pile
)

object Basic {
  val initialSetForTwoPlayers =
    Basic(
      copper = Pile(Card.Copper, 46),
      silver = Pile(Card.Silver, 40),
      gold = Pile(Card.Gold, 30),
      estate = Pile(Card.Estate, 18),
      duchy = Pile(Card.Duchy, 12),
      province = Pile(Card.Province, 12),
      curse = Pile(Card.Curse, 30)
    )

}
