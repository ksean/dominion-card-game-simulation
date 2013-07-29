package sa.ai.model

/**
 * Dominion effect
 * An effect is a source of change from one state of the game to another state
 * i.e. A treasure card would originate from a player, target the same player, and modify their usable wealth accordingly
 */
case class Effect(
  target : List[Dominion],
  source : Dominion,
  modifier : List[Modification]
)