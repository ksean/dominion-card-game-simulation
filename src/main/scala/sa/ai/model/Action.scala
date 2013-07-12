package sa.ai.model

/**
 * Dominion action
 * A card contains a list of actions to be executed in a certain order
 * i.e A treasure card would have an action with the effect of adding units of currency to a player's usable wealth
 */
case class Action(
  effect : List[Effect]
)

object Action {

}
