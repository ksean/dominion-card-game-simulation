package sa.ai.rule

import sa.ai.model._
import sa.ai.model.card._
import scala.annotation.tailrec

/**
 * http://riograndegames.com/uploads/Game/Game_278_gameRules.pdf
 */
trait Ruleset
{
  def moves(state:Game) : Set[Move]

  def transition(state:Game, move:Move) : Game


  @tailrec
  final def transition(state:Game, moves:Seq[Move]) : Game =
    moves match {
      case Nil => state
      case next :: rest => transition(transition(state, next), rest)
    }
}
