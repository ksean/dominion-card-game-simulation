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

  def subtractSupply(card: Card) : Basic =
    card match{
      
      // Treasure cards
      case Card.Copper => {
        copy(copper = copper.plusSize(-1))
      }
        
      case Card.Silver => {
        copy(silver = silver.plusSize(-1))
      }

      case Card.Gold => {
        copy(gold = gold.plusSize(-1))
      }


      // Victory cards
      case Card.Estate => {
          copy(estate = estate.plusSize(-1))
      }

      case Card.Duchy => {
        copy(duchy = duchy.plusSize(-1))
      }
        
      case Card.Province => {
        copy(province = province.plusSize(-1))
      }
      

    }


//  def withCardCount(card: Card)
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

  val initialRestrictedSetForTwoPlayers =
    Basic(
      copper   = Pile(Card.Copper, 46),
      silver   = Pile(Card.Silver, 40),
      gold     = Pile(Card.Gold, 30),
      estate   = Pile(Card.Estate, 8),
      duchy    = Pile(Card.Duchy, 8),
      province = Pile(Card.Province, 8),
      curse    = Pile(Card.Curse, 0),
      trash    = TrashPile(Seq())
    )

}
