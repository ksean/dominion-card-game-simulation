package sa.ai.model.card


/**
 * Dominion card
 */
case class Card(
  name : String,
  set : String,
  cardType : CardType,
  cost : Int,
  value : Int,
  victory : Int
) {/*
  def randomCard : Card = {

  }*/
}

object Card {
  // Base set action cards (partial list)
  val Cellar     = Card("Cellar", "Base", ActionType, 2, 0, 0)
  val Market     = Card("Market", "Base", ActionType, 5, 0, 0)
  val Militia    = Card("Militia", "Base", ActionType, 4, 0, 0)
  val Mine       = Card("Mine", "Base", ActionType, 5, 0, 0)
  val Moat       = Card("Moat", "Base", ActionType, 2, 0, 0)
  val Remodel    = Card("Remodel", "Base", ActionType, 4, 0, 0)
  val Smithy     = Card("Smithy", "Base", ActionType, 4, 0, 0)
  val Village    = Card("Village", "Base", ActionType, 3, 0, 0)
  val Woodcutter = Card("Woodcutter", "Base", ActionType,3, 0, 0)
  val Workshop   = Card("Workshop", "Base", ActionType, 3, 0, 0)

  // Base set treasure cards
  val Copper = Card("Copper", "Base", Treasure, 0, 1, 0)
  val Silver = Card("Silver", "Base", Treasure, 3, 2, 0)
  val Gold   = Card("Gold", "Base", Treasure, 6, 3, 0)

  // Base set victory cards
  val Estate   = Card("Estate", "Base", Victory, 2, 0, 1)
  val Duchy    = Card("Duchy", "Base", Victory, 5, 0, 3)
  val Province = Card("Province", "Base", Victory, 8, 0, 6)

  // Base set other cards
  val Curse = Card("Curse", "Base", CurseType, 0, 0, -1)
}