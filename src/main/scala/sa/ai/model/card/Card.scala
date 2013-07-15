package sa.ai.model.card

/**
 * Dominion card
 */
case class Card(
  name : String,
  set : String,
  cardType : String, // TODO: alternative type?
  cost : Int
  //action : List[Action]
)

object Card {
  // Base set action cards (partial list)
  val Cellar = Card("Cellar", "Base", "Action", 2)
  val Market = Card("Market", "Base", "Action", 5)
  val Militia = Card("Militia", "Base", "Action", 4)
  val Mine = Card("Mine", "Base", "Action", 5)
  val Moat = Card("Moat", "Base", "Action", 2)
  val Remodel = Card("Remodel", "Base", "Action", 4)
  val Smithy = Card("Smithy", "Base", "Action", 4)
  val Village = Card("Village", "Base", "Action", 3)
  val Woodcutter = Card("Woodcutter", "Base", "Action",3)
  val Workshop = Card("Workshop", "Base", "Action", 3)

  // Base set treasure cards
  val Copper = Card("Copper", "Base", "Treasure", 0)
  val Silver = Card("Silver", "Base", "Treasure", 3)
  val Gold = Card("Gold", "Base", "Treasure", 6)

  // Base set victory cards
  val Estate = Card("Estate", "Base", "Victory", 2)
  val Duchy = Card("Duchy", "Base", "Victory", 5)
  val Province = Card("Province", "Base", "Victory", 8)

  // Base set other cards
  val Curse = Card("Curse", "Base", "Curse", 0)

  val FirstGame = Seq(
    Cellar, Market, Militia, Mine, Moat, Remodel, Smithy, Village, Woodcutter, Workshop)
}