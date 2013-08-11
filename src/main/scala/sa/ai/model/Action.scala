package sa.ai.model

/**
 * Dominion action line
 * A card contains a list of actions to be executed in a certain order
 * i.e A treasure card would have an action line with the effect of adding units of currency to a player's usable wealth
 */
case class ActionLine(
  effect : List[Effect]
)
