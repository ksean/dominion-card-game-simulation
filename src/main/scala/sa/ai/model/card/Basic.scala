package sa.ai.model.card

/**
 * http://dominioncg.wikia.com/wiki/Basic_Cards
 */
case class Basic(
  copper   : SupplyPile,
  silver   : SupplyPile,
  gold     : SupplyPile,
  estate   : SupplyPile,
  duchy    : SupplyPile,
  province : SupplyPile,
  curse    : SupplyPile,
  trash    : TrashPile
) {
  def supply : Set[SupplyPile] =
    Set(copper, silver, gold, estate, duchy, province, curse)

  def subtractCard(card: Card) : Basic =
    card match{
      case Card.Province => {
        copy(province = province.plusSize(-1))
      }

      case Card.Silver => {
        copy(silver = silver.plusSize(-1))
      }
    }


//    ???
}

object Basic {
  val empty =
    Basic(
      SupplyPile.empty,
      SupplyPile.empty,
      SupplyPile.empty,
      SupplyPile.empty,
      SupplyPile.empty,
      SupplyPile.empty,
      SupplyPile.empty,
      TrashPile.empty
    )
  val initialSetForTwoPlayers =
    Basic(
      copper   = Pile(Card.Copper, 46),
      silver   = Pile(Card.Silver, 40),
      gold     = Pile(Card.Gold, 30),
      estate   = Pile(Card.Estate, 8),
      duchy    = Pile(Card.Duchy, 8),
      province = Pile(Card.Province, 8),
      curse    = Pile(Card.Curse, 10),
      trash    = TrashPile(Seq())
    )

}
