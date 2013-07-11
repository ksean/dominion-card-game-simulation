package sa.ai.model

/**
 * Dominion card
 */
case class Card(
  name : String
)

object Card {
  // Base set cards (partial list)
  val Cellar = Card("Cellar")
  val Market = Card("Market")
  val Militia = Card("Militia")
  val Mine = Card("Mine")
  val Moat = Card("Moat")
  val Remodel = Card("Remodel")
  val Smithy = Card("Smithy")
  val Village = Card("Village")
  val Woodcutter = Card("Woodcutter")
  val Workshop = Card("Workshop")

  val FirstGame = Set(
    Cellar, Market, Militia, Mine, Moat, Remodel, Smithy, Village, Woodcutter, Workshop)
}