package sa.ai.rule

import sa.ai.model.Game

/**
 * http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf
 */
object Ruleset {
  def actions(state:Game) : Set[Move] = Set(Shuffle(0))

  def transition(state:Game, move:Move) : Game = ???
}
