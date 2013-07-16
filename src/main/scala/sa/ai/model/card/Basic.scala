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
  curse    : Pile,
  trash    : TrashPile
)

object Basic {
  val initialSetForTwoPlayers =
    Basic(
      copper = Pile(Card.Copper, 46),
      silver = Pile(Card.Silver, 40),
      gold = Pile(Card.Gold, 30),
      estate = Pile(Card.Estate, 8),
      duchy = Pile(Card.Duchy, 8),
      province = Pile(Card.Province, 8),
      curse = Pile(Card.Curse, 10),
      trash = TrashPile(Seq())
    )

}
