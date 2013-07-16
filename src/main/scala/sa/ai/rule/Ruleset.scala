package sa.ai.model.rule

import sa.ai.model.{Action, Game}

/**
 * http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf
 */
object Ruleset {
  def actions(state:Game) : Set[Move] = Set(Shuffle(0))
}
